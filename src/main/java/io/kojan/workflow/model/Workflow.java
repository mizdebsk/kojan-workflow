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

import io.kojan.xml.Entity;
import io.kojan.xml.Relationship;
import io.kojan.xml.XMLException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mikolaj Izdebski
 */
public class Workflow {
    private final List<Task> tasks;
    private final List<Result> results;

    public Workflow(List<Task> tasks, List<Result> results) {
        this.tasks = Collections.unmodifiableList(new ArrayList<>(tasks));
        this.results = Collections.unmodifiableList(new ArrayList<>(results));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Result> getResults() {
        return results;
    }

    static final Entity<Workflow, WorkflowBuilder> ENTITY =
            Entity.of(
                    "workflow",
                    WorkflowBuilder::new,
                    Relationship.of(Task.ENTITY, Workflow::getTasks, WorkflowBuilder::addTask),
                    Relationship.of(
                            Result.ENTITY, Workflow::getResults, WorkflowBuilder::addResult));

    public static Workflow readFromXML(Reader reader) throws XMLException {
        return ENTITY.readFromXML(reader);
    }

    public static Workflow readFromXML(Path path) throws IOException, XMLException {
        return ENTITY.readFromXML(path);
    }

    public static Workflow fromXML(String xml) throws XMLException {
        return ENTITY.fromXML(xml);
    }

    public void writeToXML(Writer writer) throws XMLException {
        ENTITY.writeToXML(writer, this);
    }

    public void writeToXML(Path path) throws IOException, XMLException {
        ENTITY.writeToXML(path, this);
    }

    public String toXML() throws XMLException {
        return ENTITY.toXML(this);
    }
}
