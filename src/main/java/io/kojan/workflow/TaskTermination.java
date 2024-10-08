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

import io.kojan.workflow.model.TaskOutcome;

/**
 * @author Mikolaj Izdebski
 */
public final class TaskTermination extends Throwable {
    private static final long serialVersionUID = 1;

    private final TaskOutcome outcome;

    private TaskTermination(TaskOutcome outcome, String reason) {
        super(reason);
        this.outcome = outcome;
    }

    public TaskOutcome getOutcome() {
        return outcome;
    }

    public static TaskTermination error(String reason) throws TaskTermination {
        throw new TaskTermination(TaskOutcome.ERROR, reason);
    }

    public static TaskTermination fail(String reason) throws TaskTermination {
        throw new TaskTermination(TaskOutcome.FAILURE, reason);
    }

    public static TaskTermination success(String reason) throws TaskTermination {
        throw new TaskTermination(TaskOutcome.SUCCESS, reason);
    }
}
