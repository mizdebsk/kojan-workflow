/*-
 * Copyright (c) 2021-2025 Red Hat, Inc.
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

import io.kojan.xml.Builder;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Builder} for {@link Workflow} objects.
 *
 * @author Mikolaj Izdebski
 */
public class WorkflowBuilder implements Builder<Workflow> {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();

    /** Creates the builder with default initial state. */
    public WorkflowBuilder() {}

    /**
     * Adds a {@link Task} to the workflow being built.
     *
     * @param task task to add to the workflow under construction
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adds a {@link Result} to the workflow being built.
     *
     * @param result result to add to the workflow under construction
     */
    public void addResult(Result result) {
        results.add(result);
    }

    @Override
    public Workflow build() {
        return new Workflow(tasks, results);
    }
}
