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
 * A {@link Builder} for {@link Task} objects.
 *
 * @author Mikolaj Izdebski
 */
public class TaskBuilder implements Builder<Task> {
    private String id;
    private String handler;
    private final List<String> dependencies = new ArrayList<>();
    private final List<Parameter> parameters = new ArrayList<>();

    /** Creates the builder with default initial state. */
    public TaskBuilder() {}

    /**
     * Sets unique task ID.
     *
     * @param id unique task ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets task handler, the code that can execute this task.
     *
     * @param handler reference to code that can execute this task
     */
    public void setHandler(String handler) {
        this.handler = handler;
    }

    /**
     * Adds dependency of this task.
     *
     * @param dependency dependency of this task
     */
    public void addDependency(String dependency) {
        dependencies.add(dependency);
    }

    /**
     * Adds parameter of this task execution.
     *
     * @param parameter parameter of this task execution
     */
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    /**
     * Adds parameter of this task execution.
     *
     * @param name parameter name
     * @param value parameter value
     */
    public void addParameter(String name, String value) {
        addParameter(new Parameter(name, value));
    }

    @Override
    public Task build() {
        return new Task(id, handler, dependencies, parameters);
    }
}
