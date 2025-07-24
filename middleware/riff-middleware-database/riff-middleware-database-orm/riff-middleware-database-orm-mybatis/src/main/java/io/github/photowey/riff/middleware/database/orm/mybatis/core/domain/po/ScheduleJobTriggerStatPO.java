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
package io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po;

import java.io.Serial;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobTriggerStatPO}.
 * |- riff_schedule_job_trigger_stat
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
@TableName("riff_schedule_job_trigger_stat")
public class ScheduleJobTriggerStatPO extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = -5575407420184672502L;

    /**
     * AppID
     */
    private Long appId;
    /**
     * JobID
     */
    private Long jobId;
    /**
     * TriggerYear
     */
    private Integer triggerYear;
    /**
     * TriggerMonth
     */
    private Integer triggerMonth;
    /**
     * TriggerDay
     */
    private Integer triggerDay;
    /**
     * TriggerTime
     */
    private LocalDateTime triggerTime;
    /**
     * TriggerCount
     */
    private Integer triggerCount;
    /**
     * SuccessCount
     */
    private Integer successCount;
    /**
     * FailureCount
     */
    private Integer failureCount;
    /**
     * RetryCount
     */
    private Integer retryCount;

    // ----------------------------------------------------------------

    public Long appId() {
        return this.appId;
    }

    public Long jobId() {
        return this.jobId;
    }

    public Integer triggerYear() {
        return this.triggerYear;
    }

    public Integer triggerMonth() {
        return this.triggerMonth;
    }

    public Integer triggerDay() {
        return this.triggerDay;
    }

    public LocalDateTime triggerTime() {
        return this.triggerTime;
    }

    public Integer triggerCount() {
        return this.triggerCount;
    }

    public Integer successCount() {
        return this.successCount;
    }

    public Integer failureCount() {
        return this.failureCount;
    }

    public Integer retryCount() {
        return this.retryCount;
    }

}
