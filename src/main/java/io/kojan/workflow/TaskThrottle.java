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
 * A way to limit the pace at which {@link Task}s are executed.
 *
 * @author Mikolaj Izdebski
 */
public interface TaskThrottle {
    /**
     * Acquire required capacity to allow executing given {@link Task}. Block current thread until
     * the task can be executed. Called before given task begins executing.
     *
     * @param task the task to acquire capacity for
     */
    void acquireCapacity(Task task);

    /**
     * Release previously acquired capacity for execution of given {@link Task}. Called after given
     * task finishes executing.
     *
     * @param task the task to release capacity for
     */
    void releaseCapacity(Task task);
}
