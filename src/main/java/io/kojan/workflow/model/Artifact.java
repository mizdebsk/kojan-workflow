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
 * @author Mikolaj Izdebski
 */
public class Artifact {
    private final String type;
    private final String name;

    public Artifact(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    static final Entity<Artifact, ArtifactBuilder> ENTITY =
            Entity.of(
                    "artifact",
                    ArtifactBuilder::new,
                    Attribute.of("type", Artifact::getType, ArtifactBuilder::setType),
                    Attribute.of("name", Artifact::getName, ArtifactBuilder::setName));
}
