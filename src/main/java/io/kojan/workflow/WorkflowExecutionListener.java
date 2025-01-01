/*-
 * Copyright (c) 2023-2025 Red Hat, Inc.
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
import io.kojan.workflow.model.TaskOutcome;
import io.kojan.workflow.model.Workflow;

/**
 * An event listener that is notified about various events during {@link Workflow} execution.
 *
 * @author Mikolaj Izdebski
 */
public interface WorkflowExecutionListener {
    /**
     * Called when a task execution is about to be started.
     *
     * @param workflow the workflow being executed
     * @param task the task which execution is about to be started
     */
    void taskRunning(Workflow workflow, Task task);

    /**
     * Called when a task execution is finished with outcome {@link TaskOutcome#SUCCESS}.
     *
     * @param workflow the workflow being executed
     * @param finishedTask the task which execution has been finished
     */
    void taskSucceeded(Workflow workflow, FinishedTask finishedTask);

    /**
     * Called when a task execution is finished with outcome other than {@link TaskOutcome#SUCCESS}.
     *
     * @param workflow the workflow being executed
     * @param finishedTask the task which execution has been finished
     */
    void taskFailed(Workflow workflow, FinishedTask finishedTask);

    /**
     * Called when a task execution has been skipped because an already-existing result has been
     * found and reused for the task.
     *
     * @param workflow the workflow being executed
     * @param finishedTask the task which execution has been skipped
     */
    void taskReused(Workflow workflow, FinishedTask finishedTask);

    /**
     * Called when a workflow execution is about to be started.
     *
     * @param workflow the workflow being executed
     */
    void workflowRunning(Workflow workflow);

    /**
     * Called when a workflow execution has been finished successfully and all task results are
     * available and have successful outcome.
     *
     * @param workflow the workflow being executed
     */
    void workflowSucceeded(Workflow workflow);

    /**
     * Called when a workflow execution has been finished, but some of the tasks were not
     * successfully completed.
     *
     * @param workflow the workflow being executed
     */
    void workflowFailed(Workflow workflow);
}
