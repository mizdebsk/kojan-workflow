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

import io.kojan.xml.Attribute;
import io.kojan.xml.Entity;
import io.kojan.xml.Relationship;
import io.kojan.xml.XMLException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Task execution result.
 *
 * @author Mikolaj Izdebski
 */
public class Result {
    private final String id;
    private final String taskId;
    private final List<Artifact> artifacts;
    private final TaskOutcome outcome;
    private final String outcomeReason;
    private final LocalDateTime timeStarted;
    private final LocalDateTime timeFinished;

    /**
     * Creates a task execution result object.
     *
     * @param id unique ID of this result
     * @param taskId ID of task executed
     * @param artifacts list of artifacts produced by task execution
     * @param outcome outcome of task execution
     * @param outcomeReason reason of particular task execution outcome
     * @param timeStarted date and time task execution started
     * @param timeFinished date and time task execution finished
     */
    public Result(
            String id,
            String taskId,
            List<Artifact> artifacts,
            TaskOutcome outcome,
            String outcomeReason,
            LocalDateTime timeStarted,
            LocalDateTime timeFinished) {
        this.id = id;
        this.taskId = taskId;
        this.artifacts = Collections.unmodifiableList(new ArrayList<>(artifacts));
        this.outcome = outcome;
        this.outcomeReason = outcomeReason;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
    }

    /**
     * Determines unique ID of this result.
     *
     * @return unique ID of the result
     */
    public String getId() {
        return id;
    }

    /**
     * Determines ID of {@link Task} that was executed.
     *
     * @return ID of task executed
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Determines artifacts produced by task execution.
     *
     * @return list of artifacts produced by task execution
     */
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    /**
     * Determines outcome of task execution.
     *
     * @return outcome of task execution
     */
    public TaskOutcome getOutcome() {
        return outcome;
    }

    /**
     * Determines reason of particular task execution outcome.
     *
     * @return reason of particular task execution outcome
     */
    public String getOutcomeReason() {
        return outcomeReason;
    }

    /**
     * Determines date and time task execution started.
     *
     * @return date and time task execution started
     */
    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    /**
     * Determines date and time task execution finished.
     *
     * @return date and time task execution finished
     */
    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    static final Entity<Result, ResultBuilder> ENTITY =
            Entity.of(
                    "result",
                    ResultBuilder::new,
                    Attribute.of("id", Result::getId, ResultBuilder::setId),
                    Attribute.of("task", Result::getTaskId, ResultBuilder::setTaskId),
                    Relationship.of(
                            Artifact.ENTITY, Result::getArtifacts, ResultBuilder::addArtifact),
                    Attribute.of(
                            "outcome",
                            Result::getOutcome,
                            ResultBuilder::setOutcome,
                            TaskOutcome::toString,
                            TaskOutcome::valueOf),
                    Attribute.of(
                            "outcomeReason",
                            Result::getOutcomeReason,
                            ResultBuilder::setOutcomeReason),
                    Attribute.of(
                            "timeStarted",
                            Result::getTimeStarted,
                            ResultBuilder::setTimeStarted,
                            LocalDateTime::toString,
                            LocalDateTime::parse),
                    Attribute.of(
                            "timeFinished",
                            Result::getTimeFinished,
                            ResultBuilder::setTimeFinished,
                            LocalDateTime::toString,
                            LocalDateTime::parse));

    /**
     * Reads task execution result from an XML file at specified {@link Path}.
     *
     * @param path path to XML file to deserialize data from
     * @return task execution result deserialized from XML form
     * @throws IOException in case I/O error occurs when reading the file
     * @throws XMLException in case an exception occurs during XML deserialization
     */
    public static Result readFromXML(Path path) throws IOException, XMLException {
        return ENTITY.readFromXML(path);
    }

    /**
     * Writes task execution result as an XML file at specified {@link Path}.
     *
     * @param path path to XML file to serialize data to
     * @throws IOException in case I/O error occurs when writing the file
     * @throws XMLException in case an exception occurs during XML serialization
     */
    public void writeToXML(Path path) throws IOException, XMLException {
        ENTITY.writeToXML(path, this);
    }
}
