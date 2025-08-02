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
     * ParentID
     */
    private Long parentId;
    /**
     * ChildrenID
     */
    private Long childrenId;
    /**
     * ChildrenCode
     */
    private String childrenCode;
    /**
     * TriggerCondition
     */
    private String triggerCondition;
    /**
     * TriggerContext
     */
    private String triggerContext;

    // ----------------------------------------------------------------

    public Long parentId() {
        return this.parentId;
    }

    public Long childrenId() {
        return this.childrenId;
    }

    public String childrenCode() {
        return childrenCode;
    }

    public String triggerCondition() {
        return this.triggerCondition;
    }

    public String triggerContext() {
        return this.triggerContext;
    }

}
