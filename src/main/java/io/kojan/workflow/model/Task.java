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

import io.kojan.workflow.TaskHandler;
import io.kojan.xml.Attribute;
import io.kojan.xml.Entity;
import io.kojan.xml.Relationship;
import java.util.Collections;
import java.util.List;

/**
 * A smallest work unit. An abstract task, a thing that needs to be done.
 *
 * <p>Each task has a handler, which is a reference to a code responsible for executing the task,
 * typically a class name implementing {@link TaskHandler}. Task handlers can recognize parameters
 * in form of key-value pairs. The exact meaning of the parameters depends on particular task
 * handler.
 *
 * <p>A task may depend on zero or more other tasks that must be completed before this task can
 * begin executing.
 *
 * @author Mikolaj Izdebski
 */
public class Task {
    private final String id;
    private final String handler;
    private final List<String> dependencies;
    private final List<Parameter> parameters;

    /**
     * Creates a new task object.
     *
     * @param id unique task ID
     * @param handler reference to code that can execute this task
     * @param dependencies list of tasks that this task depends on
     * @param parameters list of task execution parameters
     */
    public Task(String id, String handler, List<String> dependencies, List<Parameter> parameters) {
        this.id = id;
        this.handler = handler;
        this.dependencies = Collections.unmodifiableList(dependencies);
        this.parameters = Collections.unmodifiableList(parameters);
    }

    /**
     * Determines the unique ID of this task.
     *
     * @return unique task ID
     */
    public String getId() {
        return id;
    }

    /**
     * Determines task handler, the code that can execute this task.
     *
     * @return reference to code that can execute this task
     */
    public String getHandler() {
        return handler;
    }

    /**
     * Determines tasks that this task depends on.
     *
     * @return list of tasks that this task depends on
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * Determines parameters that affect task execution.
     *
     * @return list of task execution parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "Task(" + id + ")";
    }

    static final Entity<Task, TaskBuilder> ENTITY =
            Entity.of(
                    "task",
                    TaskBuilder::new,
                    Attribute.of("id", Task::getId, TaskBuilder::setId),
                    Attribute.of("handler", Task::getHandler, TaskBuilder::setHandler),
                    Attribute.ofMulti(
                            "dependency", Task::getDependencies, TaskBuilder::addDependency),
                    Relationship.of(
                            Parameter.ENTITY, Task::getParameters, TaskBuilder::addParameter));
}
