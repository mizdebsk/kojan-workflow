/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
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

/**
 * Interface to task persistent and ephemeral storage. Specifies the file system locations under
 * which tasks can store their artifacts and temporary files.
 *
 * <p>Each task has a working directory, which is the directory under the task may store its
 * intermediate files that are kept only until the task is being executed, and can be discarded
 * right after task execution is finished.
 *
 * <p>Each task also has a result directory, which is the directory under the task stores its
 * artifact files, which are preserved even after task execution is finished.
 *
 * @author Mikolaj Izdebski
 */
public interface TaskStorage {
    /**
     * Obtain task result directory, which is the directory under task stores its artifact files,
     * which are preserved even after task execution is finished.
     *
     * @param task the task of which result directory should be returned
     * @param resultId ID of task result
     * @return path to the task result directory
     */
    Path getResultDir(Task task, String resultId);

    /**
     * Obtain task working directory, which is the directory under task may store intermediate files
     * that are kept only until the task is being executed, and can be discarded right after task
     * execution is finished.
     *
     * @param task the task of which working directory should be returned
     * @param resultId ID of task result
     * @return path to the task working directory
     */
    Path getWorkDir(Task task, String resultId);
}
