/*-
 * Copyright (c) 2024 Red Hat, Inc.
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

import io.kojan.workflow.model.Task;
import java.nio.file.Path;
import java.util.List;

/**
 * Context in which a task is being executed. Valid only during task execution.
 *
 * @author Mikolaj Izdebski
 */
public interface TaskExecutionContext {
    /**
     * Obtain the task that is being executed.
     *
     * @return the task that is being executed
     */
    Task getTask();

    /**
     * Obtain dependencies of the task being executed
     *
     * @return list of dependencies of the task being executed
     */
    List<FinishedTask> getDependencies();

    /**
     * Obtain task working directory, which is the directory under task may store intermediate files
     * that are kept only until the task is being executed, and can be discarded right after task
     * execution is finished.
     *
     * @return path to the task working directory
     */
    Path getWorkDir();

    /**
     * Obtain task result directory, which is the directory under task stores its artifact files,
     * which are preserved even after task execution is finished.
     *
     * @return path to the task result directory
     */
    Path getResultDir();

    /**
     * Obtain at least one artifact of direct dependency tasks of given type.
     *
     * @param type type of artifacts to obtain
     * @return list of at least one matching artifact path
     * @throws TaskTermination in case no matching artifacts were found
     */
    List<Path> getDependencyArtifacts(String type) throws TaskTermination;

    /**
     * Obtain exactly one artifact of direct dependency tasks of given type.
     *
     * @param type type of artifact to obtain
     * @return one matching artifact path
     * @throws TaskTermination in case more than one or no matching artifacts were found
     */
    Path getDependencyArtifact(String type) throws TaskTermination;

    /**
     * Add artifact file for the current task.
     *
     * <p>This method does not actually create any file in the file system. The caller is
     * responsible for creating artifact file under the returned {@link Path}.
     *
     * @param type type of the artifact to add
     * @param name unique name of the artifact to add
     * @return path to the added artifact
     */
    Path addArtifact(String type, String name);
}
