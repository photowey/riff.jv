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
import java.util.function.Consumer;

import io.github.photowey.riff.business.job.core.checker.exception.AbstractJobExceptionChecker;
import io.github.photowey.riff.business.job.core.constant.JobMessageConstants;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobChainAddPayload}.
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
public class ScheduleJobChainAddPayload extends AbstractSchedulePayload<ScheduleJobChain> {

    @Serial
    private static final long serialVersionUID = -1955677207253583956L;

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
     * The ID of the parent job, if the job has a parent.
     */
    @Schema(hidden = true)
    private Long parentId;

    // ----------------------------------------------------------------

    @Override
    public void checkActions() {
        this.checkChildIdOrCode();
    }

    // ----------------------------------------------------------------

    private void checkChildIdOrCode() {
        if (Objects.isNull(this.childId) && Strings.isEmpty(this.childCode)) {
            AbstractJobExceptionChecker.throwUnchecked(JobMessageConstants.ERROR_CHILD_JOB_ID_AND_CODE_NOT_PROVIDED);
        }
    }

    // ----------------------------------------------------------------

    public ScheduleJobChain toScheduleJobChain() {
        return ScheduleJobChain.builder()
            .childId(this.childId)
            .childCode(this.childCode)
            .triggerCondition(this.triggerCondition)
            .triggerContext(this.triggerContext)
            .build();
    }

    public ScheduleJobChain toScheduleJobChain(Consumer<ScheduleJobChain> fx) {
        ScheduleJobChain tt = this.toScheduleJobChain();
        fx.accept(tt);

        return tt;
    }
}
