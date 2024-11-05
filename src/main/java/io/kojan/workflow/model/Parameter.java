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

/**
 * Task execution parameter. A pair of name and value Strings.
 *
 * @author Mikolaj Izdebski
 */
public class Parameter {
    private final String name;
    private final String value;

    /**
     * Create a parameter with given name and value.
     *
     * @param name parameter name
     * @param value parameter value
     */
    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Determines parameter name.
     *
     * @return parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * Determines parameter value.
     *
     * @return parameter value
     */
    public String getValue() {
        return value;
    }

    static final Entity<Parameter, ParameterBuilder> ENTITY =
            Entity.of(
                    "parameter",
                    ParameterBuilder::new,
                    Attribute.of("name", Parameter::getName, ParameterBuilder::setName),
                    Attribute.of("value", Parameter::getValue, ParameterBuilder::setValue));
}
