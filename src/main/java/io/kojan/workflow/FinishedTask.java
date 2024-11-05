/*-
 * Copyright (c) 2021 Red Hat, Inc.
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
import io.kojan.workflow.model.Result;
import io.kojan.workflow.model.Task;
import java.nio.file.Path;

/**
 * Task which execution has been finished. A pair of {@link Task} and associated {@link Result}.
 *
 * @author Mikolaj Izdebski
 */
public class FinishedTask {
    private final Task task;
    private final Result result;
    private final Path resultDir;

    /**
     * Creates a finished task object.
     *
     * @param task the task that has been finished
     * @param result task result
     * @param resultDir path to directory containing task {@link Artifact}s
     */
    public FinishedTask(Task task, Result result, Path resultDir) {
        this.task = task;
        this.result = result;
        this.resultDir = resultDir;
    }

    /**
     * Obtain the task that has been finished.
     *
     * @return the task reference.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Obtain the result of the finished task.
     *
     * @return result of the finished task
     */
    public Result getResult() {
        return result;
    }

    /**
     * Obtain path to the specified task artifact.
     *
     * @param artifact artifact of which path should be returned
     * @return path to the specified task artifact
     */
    public Path getArtifact(Artifact artifact) {
        return resultDir.resolve(artifact.getName());
    }
}
