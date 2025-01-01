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
 * A compound work unit. A set of interrelated {@link Task}s that are executed until they are
 * completed. Each task may have an optional associated {@link Result}.
 *
 * @author Mikolaj Izdebski
 */
public class Workflow {
    private final List<Task> tasks;
    private final List<Result> results;

    /**
     * Creates a workflow object.
     *
     * @param tasks list of tasks that make up this workflow
     * @param results list of results of some of the tasks
     */
    public Workflow(List<Task> tasks, List<Result> results) {
        this.tasks = Collections.unmodifiableList(new ArrayList<>(tasks));
        this.results = Collections.unmodifiableList(new ArrayList<>(results));
    }

    /**
     * Determines the tasks that make up this workflow.
     *
     * @return list of tasks that make up this workflow
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Determines available results of some of the tasks that make up this workflow.
     *
     * @return list of results of some of the tasks
     */
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

    /**
     * Reads a workflow as XML from the specified {@link Reader}.
     *
     * @param reader XML stream to deserialize data from
     * @return workflow object deserialized from XML form
     * @throws XMLException in case an exception occurs during XML deserialization
     */
    public static Workflow readFromXML(Reader reader) throws XMLException {
        return ENTITY.readFromXML(reader);
    }

    /**
     * Reads a workflow from an XML file at specified {@link Path}.
     *
     * @param path path to XML file to deserialize data from
     * @return workflow object deserialized from XML form
     * @throws IOException in case I/O error occurs when reading the file
     * @throws XMLException in case an exception occurs during XML deserialization
     */
    public static Workflow readFromXML(Path path) throws IOException, XMLException {
        return ENTITY.readFromXML(path);
    }

    /**
     * Deserializes a workflow object from an XML string.
     *
     * @param xml String containing XML-encoded workflow object
     * @return workflow object deserialized from XML form
     * @throws XMLException in case an exception occurs during XML deserialization
     */
    public static Workflow fromXML(String xml) throws XMLException {
        return ENTITY.fromXML(xml);
    }

    /**
     * Writes the workflow as XML to the specified {@link Writer}.
     *
     * @param writer XML stream to serialize data to
     * @throws XMLException in case an exception occurs during XML serialization
     */
    public void writeToXML(Writer writer) throws XMLException {
        ENTITY.writeToXML(writer, this);
    }

    /**
     * Writes the workflow as an XML file at specified {@link Path}.
     *
     * @param path path to XML file to serialize data to
     * @throws IOException in case I/O error occurs when writing the file
     * @throws XMLException in case an exception occurs during XML serialization
     */
    public void writeToXML(Path path) throws IOException, XMLException {
        ENTITY.writeToXML(path, this);
    }

    /**
     * Serializes the workflow object to an XML string.
     *
     * @return String containing XML-encoded workflow object
     * @throws XMLException in case an exception occurs during XML serialization
     */
    public String toXML() throws XMLException {
        return ENTITY.toXML(this);
    }
}
