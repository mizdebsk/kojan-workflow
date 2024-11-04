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
package io.kojan.workflow;

import io.kojan.workflow.model.Task;
import io.kojan.workflow.model.TaskOutcome;
import io.kojan.workflow.model.Workflow;
import io.kojan.workflow.model.WorkflowBuilder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mikolaj Izdebski
 */
public class WorkflowExecutor {
    private final TaskHandlerFactory handlerFactory;
    private final CacheManager cacheManager;
    private final WorkflowBuilder workflowBuilder = new WorkflowBuilder();
    private final Set<Task> newTasks;
    private final Set<Task> pendingOrRunningTasks = new LinkedHashSet<>();
    private final Set<FinishedTask> successfullyFinishedTasks = new LinkedHashSet<>();
    private final Set<FinishedTask> unsuccessfullyFinishedTasks = new LinkedHashSet<>();
    private final Throttle throttle;
    private final List<WorkflowExecutionListener> listeners = new ArrayList<>();

    public WorkflowExecutor(
            Workflow wf,
            TaskHandlerFactory handlerFactory,
            CacheManager cacheManager,
            Throttle throttle,
            boolean batchMode) {
        wf.getTasks().stream().forEach(workflowBuilder::addTask);
        newTasks = new LinkedHashSet<>(wf.getTasks());
        this.handlerFactory = handlerFactory;
        this.cacheManager = cacheManager;
        this.throttle = throttle;
        if (batchMode) {
            listeners.add(new BatchLogger());
        } else {
            listeners.add(new InteractiveLogger(wf.getTasks().size()));
        }
    }

    CacheManager getCacheManager() {
        return cacheManager;
    }

    Throttle getThrottle() {
        return throttle;
    }

    public void addExecutionListener(WorkflowExecutionListener listener) {
        listeners.add(listener);
    }

    public synchronized void stateChangeFromPendingToRunning(Task task) {
        for (WorkflowExecutionListener listener : listeners) {
            listener.taskRunning(workflowBuilder.build(), task);
        }
    }

    synchronized void stateChangeFromRunningToFinished(FinishedTask finishedTask) {
        workflowBuilder.addResult(finishedTask.getResult());
        pendingOrRunningTasks.remove(finishedTask.getTask());
        if (finishedTask.getResult().getOutcome() == TaskOutcome.SUCCESS) {
            successfullyFinishedTasks.add(finishedTask);
            for (WorkflowExecutionListener listener : listeners) {
                listener.taskSucceeded(workflowBuilder.build(), finishedTask);
            }
        } else {
            unsuccessfullyFinishedTasks.add(finishedTask);
            for (WorkflowExecutionListener listener : listeners) {
                listener.taskFailed(workflowBuilder.build(), finishedTask);
            }
        }
        notify();
    }

    synchronized void stateChangeFromPendingToFinished(FinishedTask finishedTask) {
        workflowBuilder.addResult(finishedTask.getResult());
        pendingOrRunningTasks.remove(finishedTask.getTask());
        successfullyFinishedTasks.add(finishedTask);
        notify();
        for (WorkflowExecutionListener listener : listeners) {
            listener.taskReused(workflowBuilder.build(), finishedTask);
        }
    }

    public synchronized Workflow execute() {
        List<Thread> threads = new ArrayList<>();
        outer:
        for (; ; ) {
            for (Task td : newTasks) {
                List<FinishedTask> deps = new ArrayList<>();
                for (String depId : td.getDependencies()) {
                    for (FinishedTask task : successfullyFinishedTasks) {
                        if (task.getTask().getId().equals(depId)) {
                            deps.add(task);
                        }
                    }
                }

                if (deps.size() == td.getDependencies().size()) {
                    newTasks.remove(td);
                    pendingOrRunningTasks.add(td);

                    Thread thread = new TaskExecutor(this, handlerFactory, td, deps);
                    thread.start();
                    threads.add(thread);
                    continue outer;
                }
            }
            if (!pendingOrRunningTasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    for (Thread thread : threads) {
                        thread.interrupt();
                    }
                }
                continue;
            }
            break;
        }
        Workflow workflow = workflowBuilder.build();
        if (newTasks.isEmpty() && pendingOrRunningTasks.isEmpty()) {
            for (WorkflowExecutionListener listener : listeners) {
                listener.workflowSucceeded(workflow);
            }
        } else {
            for (WorkflowExecutionListener listener : listeners) {
                listener.workflowFailed(workflow);
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                for (Thread thread2 : threads) {
                    thread2.interrupt();
                }
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }

        return workflow;
    }
}
