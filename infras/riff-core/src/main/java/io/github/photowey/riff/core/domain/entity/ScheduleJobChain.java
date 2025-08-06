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
package io.github.photowey.riff.core.domain.entity;

import java.io.Serial;

import io.github.photowey.riff.infras.common.enums.CommonDictionary;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobChain}.
 * |- riff_schedule_job_chain
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScheduleJobChain extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = 4022075619235143975L;

    /**
     * The ID of the parent job, if the job has a parent.
     */
    private Long parentId;
    /**
     * The ID of the child job, if the child job has been created.
     */
    private Long childId;

    /**
     * The code of the child job, if the child job has been defined.
     */
    private String childCode;

    /**
     * The condition that triggers the execution of the child job.
     */
    private String triggerCondition;

    /**
     * Additional context or parameters passed when triggering the child job.
     * Typically includes key-value pairs of runtime variables, environment data,
     * or input parameters. Supports {@code SpEL} (Spring Expression Language)
     * for dynamic evaluation.
     */
    private String triggerContext;

    // ----------------------------------------------------------------

    /**
     * This fields are used to determine whether the parent job ID (parentId) needs to be checked.
     * |- 0: No need to check the parent job ID.
     * |- 1: Need to check the parent job ID.
     */
    private Integer checkParent;
    private Integer checkChildCode;

    // ----------------------------------------------------------------

    public boolean determineNeedCheckParent() {
        return Objects.defaultIfNull(
            this.checkParent, CommonDictionary.Boolean.TRUE.value()) == CommonDictionary.Boolean.TRUE.value();
    }

    public boolean determineNeedCheckChildCode() {
        return Objects.defaultIfNull(
            this.checkChildCode, CommonDictionary.Boolean.TRUE.value()) == CommonDictionary.Boolean.TRUE.value();
    }

    public void initChildIdIfNecessary() {
        if (Objects.isNull(this.childId)) {
            this.childId = 0L;
        }
    }

    // ----------------------------------------------------------------

    public Long parentId() {
        return this.parentId;
    }

    public Long childId() {
        return this.childId;
    }

    public String childCode() {
        return childCode;
    }

    public String triggerCondition() {
        return this.triggerCondition;
    }

    public String triggerContext() {
        return this.triggerContext;
    }

}
