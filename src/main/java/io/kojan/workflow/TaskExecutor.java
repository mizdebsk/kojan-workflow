/*-
 * Copyright (c) 2021-2025 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kojan.workflow;

import io.kojan.workflow.model.Artifact;
import io.kojan.workflow.model.Parameter;
import io.kojan.workflow.model.Result;
import io.kojan.workflow.model.Task;
import io.kojan.workflow.model.TaskOutcome;
import io.kojan.xml.XMLException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mikolaj Izdebski
 */
class TaskExecutor extends Thread implements TaskExecutionContext {
    private final WorkflowExecutor wfe;
    private final TaskHandlerFactory handlerFactory;
    private final Task task;
    private final List<FinishedTask> dependencies;
    private final String resultId;
    private final Path resultDir;
    private final Path workDir;
    private final List<Artifact> artifacts = new ArrayList<>();

    public TaskExecutor(
            WorkflowExecutor wfe,
            TaskHandlerFactory handlerFactory,
            Task task,
            List<FinishedTask> dependencies) {
        this.wfe = wfe;
        this.handlerFactory = handlerFactory;
        this.task = task;
        this.dependencies = Collections.unmodifiableList(dependencies);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getTask().getHandler().getBytes());
            md.update(Byte.MIN_VALUE);
            for (Parameter param : getTask().getParameters()) {
                md.update(param.getName().getBytes());
                md.update(Byte.MIN_VALUE);
                md.update(param.getValue().getBytes());
                md.update(Byte.MIN_VALUE);
            }
            for (FinishedTask dependency : getDependencies()) {
                md.update(dependency.getResult().getId().getBytes());
                md.update(Byte.MIN_VALUE);
            }
            byte[] digest = md.digest();
            this.resultId =
                    new BigInteger(1, digest)
                            .setBit(digest.length << 3)
                            .toString(16)
                            .substring(1)
                            .toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        this.resultDir = wfe.getStorage().getResultDir(task, resultId);
        this.workDir = wfe.getStorage().getWorkDir(task, resultId);
    }

    public Task getTask() {
        return task;
    }

    public List<FinishedTask> getDependencies() {
        return dependencies;
    }

    public Path getWorkDir() {
        return workDir;
    }

    public Path getResultDir() {
        return resultDir;
    }

    public List<Path> getDependencyArtifacts(String type) throws TaskTermination {
        List<Path> artifacts = new ArrayList<>();

        for (FinishedTask dependency : getDependencies()) {
            for (Artifact dependencyArtifact : dependency.getResult().getArtifacts()) {
                if (dependencyArtifact.getType().equals(type)) {
                    artifacts.add(dependency.getArtifact(dependencyArtifact));
                }
            }
        }

        return artifacts;
    }

    public Path getDependencyArtifact(String type) throws TaskTermination {
        List<Path> artifacts = getDependencyArtifacts(type);

        if (artifacts.size() != 1) {
            TaskTermination.error(
                    task
                            + " was expected to have exactly one dependency artifact of type "
                            + type
                            + ", but found "
                            + artifacts.size());
        }

        return artifacts.iterator().next();
    }

    public Path addArtifact(String type, String name) {
        Artifact artifact = new Artifact(type, name);
        artifacts.add(artifact);
        return resultDir.resolve(artifact.getName());
    }

    private void deleteDirectoryIfExists(Path dir) throws IOException {
        if (dir != null && Files.isDirectory(dir)) {
            Files.walk(dir)
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        }
    }

    private void initializeTaskDirectories() throws TaskTermination {
        try {
            Files.createDirectories(resultDir.getParent());
            deleteDirectoryIfExists(resultDir);
            Files.createDirectory(resultDir);
            Files.createDirectories(workDir.getParent());
            deleteDirectoryIfExists(workDir);
            Files.createDirectory(workDir);
        } catch (IOException e) {
            throw TaskTermination.error(
                    "I/O error when creating task directories: " + e.getMessage());
        }
    }

    private void cleanupTaskDirectories() throws TaskTermination {
        try {
            deleteDirectoryIfExists(workDir);
        } catch (IOException e) {
            throw TaskTermination.error(
                    "I/O error when deleting task work directory: " + e.getMessage());
        }
    }

    private TaskTermination handleTask() {
        try {
            initializeTaskDirectories();

            try {
                TaskHandler handler = handlerFactory.createTaskHandler(task);
                handler.handleTask(this);
                throw TaskTermination.error("Task did not set explicit outcome");
            } finally {
                cleanupTaskDirectories();
            }
        } catch (TaskTermination termination) {
            return termination;
        }
    }

    @Override
    public void run() {
        if (Files.isRegularFile(resultDir.resolve("stamp"))) {
            try {
                Result cachedResult = Result.readFromXML(resultDir.resolve("result.xml"));

                // All dependency tasks completed before cached result was even
                // started?
                if (getDependencies().stream()
                        .allMatch(
                                dep ->
                                        dep.getResult()
                                                        .getTimeFinished()
                                                        .compareTo(cachedResult.getTimeStarted())
                                                <= 0)) {
                    FinishedTask finishedTask =
                            new FinishedTask(getTask(), cachedResult, resultDir);
                    wfe.stateChangeFromPendingToFinished(finishedTask);
                    return;
                }
            } catch (IOException | XMLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            wfe.getThrottle().acquireCapacity(task);
            wfe.stateChangeFromPendingToRunning(task);

            LocalDateTime timeStarted = LocalDateTime.now();
            TaskTermination termination = handleTask();
            LocalDateTime timeFinished = LocalDateTime.now();

            Result result =
                    new Result(
                            resultId,
                            task.getId(),
                            artifacts,
                            termination.getOutcome(),
                            termination.getMessage(),
                            timeStarted,
                            timeFinished);
            if (result.getOutcome() == TaskOutcome.SUCCESS) {
                try {
                    result.writeToXML(resultDir.resolve("result.xml"));
                    Files.createFile(resultDir.resolve("stamp"));
                } catch (IOException | XMLException e) {
                    throw new RuntimeException(e);
                }
            }
            FinishedTask finishedTask = new FinishedTask(getTask(), result, resultDir);
            wfe.stateChangeFromRunningToFinished(finishedTask);
        } finally {
            wfe.getThrottle().releaseCapacity(task);
        }
    }
}
