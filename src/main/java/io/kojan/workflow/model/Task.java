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

import io.kojan.xml.Attribute;
import io.kojan.xml.Entity;
import io.kojan.xml.Relationship;
import java.util.Collections;
import java.util.List;

/**
 * @author Mikolaj Izdebski
 */
public class Task {
    private final String id;
    private final String handler;
    private final List<String> dependencies;
    private final List<Parameter> parameters;

    public Task(String id, String handler, List<String> dependencies, List<Parameter> parameters) {
        this.id = id;
        this.handler = handler;
        this.dependencies = Collections.unmodifiableList(dependencies);
        this.parameters = Collections.unmodifiableList(parameters);
    }

    public Task(Task descriptor) {
        this.id = descriptor.getId();
        this.handler = descriptor.getHandler();
        this.dependencies = descriptor.getDependencies();
        this.parameters = descriptor.getParameters();
    }

    public String getId() {
        return id;
    }

    public String getHandler() {
        return handler;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

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
