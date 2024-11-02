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
import io.kojan.xml.XMLException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
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

    public String getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public TaskOutcome getOutcome() {
        return outcome;
    }

    public String getOutcomeReason() {
        return outcomeReason;
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

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

    public static Result readFromXML(Path path) throws IOException, XMLException {
        return ENTITY.readFromXML(path);
    }

    public void writeToXML(Path path) throws IOException, XMLException {
        ENTITY.writeToXML(path, this);
    }
}
