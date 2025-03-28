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
 * A {@link Builder} for {@link Artifact} objects.
 *
 * @author Mikolaj Izdebski
 */
public class ArtifactBuilder implements Builder<Artifact> {
    private String type;
    private String name;

    /** Creates the builder with default initial state. */
    public ArtifactBuilder() {}

    /**
     * Sets artifact type.
     *
     * @param type artifact type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets artifact name.
     *
     * @param name artifact name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Artifact build() {
        return new Artifact(type, name);
    }
}
