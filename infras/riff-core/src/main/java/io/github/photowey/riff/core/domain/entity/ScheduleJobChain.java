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

import java.time.LocalDateTime;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractEntity;

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
public class ScheduleJobChain extends AbstractEntity {
    /**
     * ID
     */
    private Long id;
    /**
     * CreateBy
     */
    private Long createBy;
    /**
     * UpdateBy
     */
    private Long updateBy;
    /**
     * CreateTime
     */
    private LocalDateTime createTime;
    /**
     * UpdateTime
     */
    private LocalDateTime updateTime;
    /**
     * Deleted 0:Normal 1:Deleted
     */
    private Integer deleted;
    /**
     * Tenant
     */
    private String tenant;
    /**
     * Platform
     */
    private String platform;
    /**
     * App
     */
    private String app;
    /**
     * ParentID
     */
    private Long parentId;
    /**
     * ChildrenID
     */
    private Long childrenId;
    /**
     * TriggerCondition
     */
    private String triggerCondition;
    /**
     * TriggerContext
     */
    private String triggerContext;

    // ----------------------------------------------------------------
    public Long id() {
        return this.id;
    }

    public Long createBy() {
        return this.createBy;
    }

    public Long updateBy() {
        return this.updateBy;
    }

    public LocalDateTime createTime() {
        return this.createTime;
    }

    public LocalDateTime updateTime() {
        return this.updateTime;
    }

    public Integer deleted() {
        return this.deleted;
    }

    public String tenant() {
        return this.tenant;
    }

    public String platform() {
        return this.platform;
    }

    public String app() {
        return this.app;
    }

    public Long parentId() {
        return this.parentId;
    }

    public Long childrenId() {
        return this.childrenId;
    }

    public String triggerCondition() {
        return this.triggerCondition;
    }

    public String triggerContext() {
        return this.triggerContext;
    }

}
