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
 * @author Mikolaj Izdebski
 */
public interface TaskExecutionContext {
    Task getTask();

    List<FinishedTask> getDependencies();

    Path getWorkDir();

    Path getResultDir();

    List<Path> getDependencyArtifacts(String type) throws TaskTermination;

    Path getDependencyArtifact(String type) throws TaskTermination;

    Path addArtifact(String type, String name);
}
