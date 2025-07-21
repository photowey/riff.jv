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
 * {@code ScheduleJobTriggerRecord}.
 * |- riff_schedule_job_trigger_record
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
public class ScheduleJobTriggerRecord extends AbstractEntity {
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
     * AppID
     */
    private Long appId;
    /**
     * JobID
     */
    private Long jobId;
    /**
     * ClientID
     */
    private Long clientId;
    /**
     * ShardingContext
     */
    private String shardingContext;
    /**
     * TriggerTime
     */
    private LocalDateTime triggerTime;
    /**
     * FinishedTime
     */
    private LocalDateTime finishedTime;
    /**
     * TriggerStatus 1:Online 2:Unhealthy 4:Suspect 8:Offline
     */
    private Integer triggerStatus;
    /**
     * TriggerContext
     */
    private String triggerContext;
    /**
     * TriggerSource
     */
    private String triggerSource;
    /**
     * TriggerCode
     */
    private String triggerCode;
    /**
     * TriggerMessage
     */
    private String triggerMessage;
    /**
     * RetryCount
     */
    private Integer retryCount;
    /**
     * HandleTime
     */
    private LocalDateTime handleTime;
    /**
     * HandleCode
     */
    private String handleCode;
    /**
     * HandleMessage
     */
    private String handleMessage;
    /**
     * HandleStatus
     */
    private Integer handleStatus;
    /**
     * AlarnType
     */
    private Integer alarmType;
    /**
     * AlarmStatus
     */
    private Integer alarmStatus;
    /**
     * AlarmContext
     */
    private String alarmContext;

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

    public Long appId() {
        return this.appId;
    }

    public Long jobId() {
        return this.jobId;
    }

    public Long clientId() {
        return this.clientId;
    }

    public String shardingContext() {
        return this.shardingContext;
    }

    public LocalDateTime triggerTime() {
        return this.triggerTime;
    }

    public LocalDateTime finishedTime() {
        return this.finishedTime;
    }

    public Integer triggerStatus() {
        return this.triggerStatus;
    }

    public String triggerContext() {
        return this.triggerContext;
    }

    public String triggerSource() {
        return this.triggerSource;
    }

    public String triggerCode() {
        return this.triggerCode;
    }

    public String triggerMessage() {
        return this.triggerMessage;
    }

    public Integer retryCount() {
        return this.retryCount;
    }

    public LocalDateTime handleTime() {
        return this.handleTime;
    }

    public String handleCode() {
        return this.handleCode;
    }

    public String handleMessage() {
        return this.handleMessage;
    }

    public Integer handleStatus() {
        return this.handleStatus;
    }

    public Integer alarmType() {
        return this.alarmType;
    }

    public Integer alarmStatus() {
        return this.alarmStatus;
    }

    public String alarmContext() {
        return this.alarmContext;
    }

}
