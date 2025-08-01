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
import java.time.LocalDateTime;

import io.github.photowey.riff.infras.common.enums.CommonDictionary;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJob}.
 * |- riff_schedule_job
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
public class ScheduleJob extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = 6896991042796291427L;

    /**
     * AppID
     */
    private Long appId;
    /**
     * JobCode
     */
    private String jobCode;
    /**
     * JobName
     */
    private String jobName;
    /**
     * JobType 1:HandlerJob 2:ScriptJob 3:HttpJob
     */
    private Integer jobType;
    /**
     * HandlerName
     */
    private String handlerName;
    /**
     * DeclaredClass
     */
    private String declaredClass;
    /**
     * Method
     */
    private String method;
    /**
     * Arguments
     */
    private String arguments;

    /**
     * ScheduleType 1:Schedule once 2:Cron 3:FixedRate 4:FixedDelay
     *
     * <p>
     * 1: Schedule once - Execute the task only once.
     * 2: Cron - Execute the task based on a cron expression.
     * 3: FixedRate - Execute the task at a fixed interval, measured from the start time of the previous execution.
     * 4: FixedDelay - Execute the task at a fixed interval, measured from the completion time of the previous execution
     */
    private Integer scheduleType;
    /**
     * {@code riff://trigger/once?initialDelay=0&delay=0}
     * {@code riff://trigger/cron?expression=0/5_*_*_*_*_?&initialDelay=0&delay=0}
     * {@code riff://trigger/fixedrate?initialDelay=0&delay=0}
     * {@code riff://trigger/fixeddelay?initialDelay=0&delay=0}
     */
    private String scheduleContext;

    /**
     * Misfire strategy 1: Skip 2: Fire now
     *
     * <p>
     * 1: Skip|Cancel
     * 2: Fire now
     */
    private Integer misfireStrategy;
    /**
     * Route strategy 1: First 2: Last 3: Round 4: Random 5: Consistent hash
     *
     * <p>
     * 1: First
     * 2: Last
     * 3: Round
     * 4: Random
     * 5: Consistent hash
     */
    private Integer routeStrategy;
    /**
     * Block strategy 1.Serial execution 2.Discard later 3.Cover early
     *
     * <p>
     * 1.Serial execution
     * 2.Discard later
     * 3.Cover early
     */
    private Integer blockStrategy;

    private Integer timeoutSeconds;
    private Integer retryCount;

    /**
     * Trigger status: 1: Not started 2: In progress 3: Completed
     */
    private Integer triggerStatus;
    private LocalDateTime triggerLastTime;
    private LocalDateTime triggerNextTime;

    // ----------------------------------------------------------------

    /**
     * This field does not exist in the database.
     * |- 0 | 1
     */
    @Schema(hidden = true)
    private Integer registered;

    // ----------------------------------------------------------------

    public boolean determineIsRegistered() {
        return Objects.isNotNull(this.registered) && this.registered == CommonDictionary.Boolean.TRUE.value();
    }

    // ----------------------------------------------------------------

    public void injectTenantBase(ScheduleApp app) {
        if (Strings.isEmpty(this.tenant())) {
            this.setTenant(app.tenant());
        }

        if (Strings.isEmpty(this.platform())) {
            this.setPlatform(app.platform());
        }

        if (Strings.isEmpty(this.app())) {
            this.setApp(app.app());
        }
    }

    // ----------------------------------------------------------------

    public Long appId() {
        return this.appId;
    }

    public String jobCode() {
        return this.jobCode;
    }

    public String jobName() {
        return this.jobName;
    }

    public Integer jobType() {
        return this.jobType;
    }

    public String handlerName() {
        return this.handlerName;
    }

    public String declaredClass() {
        return this.declaredClass;
    }

    public String method() {
        return this.method;
    }

    public String arguments() {
        return this.arguments;
    }

    public Integer scheduleType() {
        return scheduleType;
    }

    public String scheduleContext() {
        return scheduleContext;
    }

    public Integer misfireStrategy() {
        return misfireStrategy;
    }

    public Integer routeStrategy() {
        return routeStrategy;
    }

    public Integer blockStrategy() {
        return blockStrategy;
    }

    public Integer timeoutSeconds() {
        return timeoutSeconds;
    }

    public Integer retryCount() {
        return retryCount;
    }

    public Integer triggerStatus() {
        return triggerStatus;
    }

    public LocalDateTime triggerLastTime() {
        return triggerLastTime;
    }

    public LocalDateTime triggerNextTime() {
        return triggerNextTime;
    }

    public Integer registered() {
        return registered;
    }
}
