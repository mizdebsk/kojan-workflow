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
class BatchLogger implements WorkflowExecutionListener {
    private void log(Object... args) {
        StringBuilder sb = new StringBuilder();

        for (Object arg : args) {
            sb.append(arg.toString());
        }

        System.err.println(sb);
    }

    @Override
    public void taskRunning(Workflow workflow, Task task) {
        log(task, " running");
    }

    @Override
    public void taskSucceeded(Workflow workflow, FinishedTask finishedTask) {
        log(
                finishedTask.getTask(),
                " finished; outcome is ",
                finishedTask.getResult().getOutcome(),
                ", reason: ",
                finishedTask.getResult().getOutcomeReason());
    }

    @Override
    public void taskFailed(Workflow workflow, FinishedTask finishedTask) {
        log(
                finishedTask.getTask(),
                " finished; outcome is ",
                finishedTask.getResult().getOutcome(),
                ", reason: ",
                finishedTask.getResult().getOutcomeReason());
    }

    @Override
    public void taskReused(Workflow workflow, FinishedTask finishedTask) {
        log(finishedTask.getTask(), " cached result was reused");
    }

    @Override
    public void workflowSucceeded(Workflow workflow) {
        log("Workflow complete");
    }

    @Override
    public void workflowFailed(Workflow workflow) {
        log("Workflow INCOMPLETE");
    }
}
