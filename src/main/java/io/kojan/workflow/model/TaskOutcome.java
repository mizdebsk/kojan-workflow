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
package io.kojan.workflow.model;

/**
 * Outcome of task execution.
 *
 * @author Mikolaj Izdebski
 */
public enum TaskOutcome {
    /** Indicates that the task was successfully completed. */
    SUCCESS,
    /** Indicates that the task was completed but failed. */
    FAILURE,
    /** Indicates that the task was not completed due to an error. */
    ERROR
}
