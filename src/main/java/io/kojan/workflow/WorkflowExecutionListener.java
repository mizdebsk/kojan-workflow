/*-
 * Copyright (c) 2023 Red Hat, Inc.
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
import io.kojan.workflow.model.Workflow;

/**
 * @author Mikolaj Izdebski
 */
public interface WorkflowExecutionListener {
    void taskRunning(Workflow workflow, Task task);

    void taskSucceeded(Workflow workflow, FinishedTask finishedTask);

    void taskFailed(Workflow workflow, FinishedTask finishedTask);

    void taskReused(Workflow workflow, FinishedTask finishedTask);

    void workflowRunning(Workflow workflow);

    void workflowSucceeded(Workflow workflow);

    void workflowFailed(Workflow workflow);
}
