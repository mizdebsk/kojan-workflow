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

import io.kojan.workflow.model.Task;

/**
 * The code capable of executing a particular {@link Task}.
 *
 * @author Mikolaj Izdebski
 */
public interface TaskHandler {
    /**
     * Executes the task in the specified context.
     *
     * <p>This method never returns and always throws appropriate {@link TaskTermination} throwable
     * to indicate the outcome of task execution.
     *
     * @param context task execution context containing the task to execute
     * @throws TaskTermination always to indicate outcome of task execution
     */
    void handleTask(TaskExecutionContext context) throws TaskTermination;
}
