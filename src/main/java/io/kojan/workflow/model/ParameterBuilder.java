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

/**
 * A {@link Builder} for {@link Parameter} objects.
 *
 * @author Mikolaj Izdebski
 */
public class ParameterBuilder implements Builder<Parameter> {
    private String name;
    private String value;

    /** Creates the builder with default initial state. */
    public ParameterBuilder() {}

    /**
     * Sets parameter name.
     *
     * @param name parameter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets parameter value.
     *
     * @param value parameter value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Parameter build() {
        return new Parameter(name, value);
    }
}
