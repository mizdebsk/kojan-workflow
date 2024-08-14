/*-
 * Copyright (c) 2024 Red Hat, Inc.
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import io.kojan.workflow.model.Task;

/**
 * @author Mikolaj Izdebski
 */
public class TaskHandlerFactory extends Thread {
    private final Map<String, Function<Task, ? extends TaskHandler>> registry = new LinkedHashMap<>();

    public void registerHandler(Class<? extends TaskHandler> cls, Function<Task, ? extends TaskHandler> ctor) {
        registry.put(cls.getCanonicalName(), ctor);
    }

    public TaskHandler createTaskHandler(Task task) {
        Function<Task, ? extends TaskHandler> ctor = registry.get(task.getHandler());
        if (ctor == null) {
            throw new IllegalArgumentException("Unsupported task handler: " + task.getHandler());
        }
        return ctor.apply(task);
    }
}
