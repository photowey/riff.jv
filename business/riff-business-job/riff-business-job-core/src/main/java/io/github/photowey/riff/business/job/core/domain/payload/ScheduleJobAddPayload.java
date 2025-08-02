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
import java.util.List;
import java.util.function.Consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.validator.annotation.AllowableValues;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobAddPayload}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScheduleJobAddPayload extends AbstractSchedulePayload<ScheduleJob> {

    @Serial
    private static final long serialVersionUID = -3642962623917300066L;

    @NotBlank(message = "The job code is required.")
    @Schema(
        description = "The unique identifier of the job. Must be a valid Java-like identifier",
        example = "io.github.photowey.riff.order.timeout.close",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String jobCode;

    @NotBlank(message = "The job name is required.")
    @Schema(
        description = "Human-readable name of the job, describing its purpose.",
        example = "Payment not completed in time, order closed",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String jobName;

    /**
     * Job type
     * |- 1: HandlerJob 2: ScriptJob 3: HttpJob
     */
    @NotNull(message = "The job type is required.")
    @Schema(
        description = "Type of the job: 1: HandlerJob(Java class) 2: ScriptJob (Groovy/JS) 3: HttpJob (HTTP endpoint).",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"1", "2", "3"}
    )
    @AllowableValues(value = "1,2,3", message = "The job type is incorrect.")
    private Integer jobType;

    @NotBlank(message = "The job handler name is required.")
    @Schema(
        description = "Name of the handler bean or script that executes the job logic.",
        example = "orderTimeoutHandler",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String handlerName;

    @Schema(
        description = "Fully qualified class name of the job handler implementation. This field is optional.",
        example = "io.github.photowey.riff.order.timeout.close.OrderTimeoutHandler",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String declaredClass;

    @Schema(
        description = "Method name in the handler class that will be invoked to execute the job.",
        example = "handle",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String method;

    @Schema(
        description = "Arguments passed to the handler method, represented as a JSON array string. <br/>"
            + "This field is optional.",
        example = "[\"abc\", 123, true]",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String arguments;

    // ----------------------------------------------------------------

    /**
     * ScheduleType 1:Schedule once 2:Cron 3:FixedRate 4:FixedDelay
     *
     * <p>
     * 1: Schedule once - Execute the task only once.
     *
     * <p>
     * 2: Cron - Execute the task based on a cron expression.
     *
     * <p>
     * 3: FixedRate - Execute the task at a fixed interval, measured from the start time of the previous execution.
     *
     * <p>
     * 4: FixedDelay - Execute the task at a fixed interval, measured from the completion time of the previous execution
     */
    @NotNull(message = "The schedule type is required.")
    @Schema(
        description = "Type of scheduling strategy: 1: Once, 2: Cron, 3: FixedRate, 4: FixedDelay.",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"1", "2", "3", "4"}
    )
    @AllowableValues(value = "1,2,3,4", message = "The schedule type is incorrect.")
    private Integer scheduleType;

    /**
     * Scheduling context in a custom URI format (e.g., {@code riff://trigger/cron}).
     * The query parameters define scheduling behavior such as cron expressions or delays.
     *
     * <p>
     * Example: {@code riff://trigger/cron?expression=0%2F5+*+*+*+*+%3F&initialDelay=0&delay=0}
     *
     * <p>
     * Note: Spaces in cron expressions must be URL-encoded (e.g., '+' or '%20').
     */
    @NotBlank(message = "The schedule context is required.")
    @Schema(
        description = "Scheduling configuration in a custom URI format. "
            + "Supports triggers like cron expressions. "
            + "Spaces in values must be URL-encoded (e.g., '+' for space).",
        example = "riff://trigger/cron?expression=0%2F5+*+*+*+*+%3F&initialDelay=0&delay=0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String scheduleContext;

    /**
     * Misfire strategy 1: Skip 2: Fire now
     *
     * <p>
     * 1: Skip
     * 2: Fire now
     */
    @NotNull(message = "The misfire strategy is required.")
    @Schema(
        description = "Strategy to handle misfired triggers: 1: Skip, 2: Fire now.",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"1", "2"}
    )
    @AllowableValues(value = "1,2", message = "The misfire strategy is incorrect.")
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
    @NotNull(message = "The route strategy is required.")
    @Schema(
        description = "Strategy for routing job execution across instances:<br/> "
            + "1: First, 2: Last, 3: Round Robin, 4: Random, 5: Consistent Hash.",
        example = "3",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"1", "2", "3", "4", "5"}
    )
    @AllowableValues(value = "1,2,3,4,5", message = "The route strategy is incorrect.")
    private Integer routeStrategy;

    /**
     * Block strategy 1.Serial execution 2.Discard later 3.Cover early
     *
     * <p>
     * 1.Serial execution
     * 2.Discard later
     * 3.Cover early
     */
    @NotNull(message = "The block strategy is required.")
    @Schema(
        description = "Strategy when job execution is blocked: 1: Serial execution, 2: Discard later, 3: Cover early.",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"1", "2", "3"}
    )
    @AllowableValues(value = "1,2,3", message = "The block strategy is incorrect.")
    private Integer blockStrategy;

    @NotNull(message = "The timeout seconds is required.")
    @Schema(
        description = "Maximum execution time for the job in seconds. 0 means no timeout.",
        example = "30",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer timeoutSeconds;

    @NotNull(message = "The retry count is required.")
    @Schema(
        description = "Number of retry attempts if job execution fails.",
        example = "3",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer retryCount;

    // ----------------------------------------------------------------

    @Schema(
        description = "The IDs of child jobs, used for job chaining. This field is optional.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<Long> childrenIds;

    @Schema(
        description = "The codes of child jobs, used for job chaining. This field is optional.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<String> childrenCodes;

    // ----------------------------------------------------------------

    /**
     * 应用 ID，用于权限与归属隔离。
     */
    @Schema(hidden = true)
    private Long appId;

    // ----------------------------------------------------------------

    public ScheduleJob toScheduleJob() {
        // ScheduleApp(id,AK|AS) -> LoginUser
        LoginUser authenticated = LoginUserHolder.mustGet();

        return ScheduleJob.builder()
            .tenant(this.tenant)
            .platform(this.platform)
            .app(this.app)
            // ----------------------------------------------------------------
            .appId(this.appId)
            .jobCode(this.jobCode)
            .jobName(this.jobName)
            .jobType(this.jobType)
            // ----------------------------------------------------------------
            .handlerName(this.handlerName)
            .declaredClass(this.declaredClass)
            .method(this.method)
            .arguments(this.arguments)
            // ----------------------------------------------------------------
            .scheduleType(this.scheduleType)
            .scheduleContext(this.scheduleContext)
            .misfireStrategy(this.misfireStrategy)
            .routeStrategy(this.routeStrategy)
            .blockStrategy(this.blockStrategy)
            .timeoutSeconds(this.timeoutSeconds)
            .retryCount(this.retryCount)
            // ----------------------------------------------------------------
            .childrenIds(this.childrenIds)
            .childrenCodes(this.childrenCodes)
            // ----------------------------------------------------------------
            .appId(authenticated.userId())
            .build();
    }

    public ScheduleJob toScheduleJob(Consumer<ScheduleJob> fx) {
        ScheduleJob tt = this.toScheduleJob();
        fx.accept(tt);

        return tt;
    }
}
