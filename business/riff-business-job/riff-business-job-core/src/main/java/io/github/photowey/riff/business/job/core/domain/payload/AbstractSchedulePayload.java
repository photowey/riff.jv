/*
 * Copyright (c) 2025-present
 * the original author(photowey<photowey@gmail.com>) or authors All rights reserved.
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
package io.github.photowey.riff.business.job.core.domain.payload;

import java.io.Serial;

import io.github.photowey.riff.infras.model.payload.AbstractPayload;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractSchedulePayload}.
 *
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSchedulePayload<T> extends AbstractPayload<T> {

    @Serial
    private static final long serialVersionUID = 8694265598345444535L;

    /**
     * Tenant
     * |- Default: saas
     */
    @Schema(
        description = "The Tenant, default: saas",
        example = "saas",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    protected String tenant;
    /**
     * Platform
     * |- Default: saas
     */
    @Schema(
        description = "The Platform, default: saas",
        example = "saas",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    protected String platform;
    /**
     * App
     * |- Default: saas
     */
    @Schema(
        description = "The App, default: boss",
        example = "boss",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    protected String app;
}
