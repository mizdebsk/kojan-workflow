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

import io.kojan.xml.Builder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Builder} for {@link Result} objects.
 *
 * @author Mikolaj Izdebski
 */
public class ResultBuilder implements Builder<Result> {
    private String id;
    private String taskId;
    private final List<Artifact> artifacts = new ArrayList<>();
    private TaskOutcome outcome;
    private String outcomeReason;
    private LocalDateTime timeStarted;
    private LocalDateTime timeFinished;

    /** Creates the builder with default initial state. */
    public ResultBuilder() {}

    /**
     * Sets unique ID of this result.
     *
     * @param id unique ID of this result
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets ID of task executed.
     *
     * @param taskId ID of task executed
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Add artifact produced by task execution.
     *
     * @param artifact artifact to add
     */
    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }

    /**
     * Sets outcome of task execution.
     *
     * @param outcome outcome of task execution
     */
    public void setOutcome(TaskOutcome outcome) {
        this.outcome = outcome;
    }

    /**
     * Sets reason of particular task execution outcome.
     *
     * @param outcomeReason reason of particular task execution outcome
     */
    public void setOutcomeReason(String outcomeReason) {
        this.outcomeReason = outcomeReason;
    }

    /**
     * Sets date and time task execution started.
     *
     * @param timeStarted date and time task execution started
     */
    public void setTimeStarted(LocalDateTime timeStarted) {
        this.timeStarted = timeStarted;
    }

    /**
     * Sets date and time task execution finished.
     *
     * @param timeFinished date and time task execution finished
     */
    public void setTimeFinished(LocalDateTime timeFinished) {
        this.timeFinished = timeFinished;
    }

    @Override
    public Result build() {
        return new Result(id, taskId, artifacts, outcome, outcomeReason, timeStarted, timeFinished);
    }
}
