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
package io.github.photowey.riff.business.job.service.calculator.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.github.photowey.riff.business.job.core.context.ScheduleContext;
import io.github.photowey.riff.business.job.service.calculator.FixedRateTriggerTimeCalculator;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.infras.common.datetime.LocalDateTimes;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code DefaultFixedRateTriggerTimeCalculator}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/01
 */
@Slf4j
@Component
public class DefaultFixedRateTriggerTimeCalculator
    extends AbstractBeanFactoryHolder implements FixedRateTriggerTimeCalculator {

    @Override
    public void handle(ScheduleContext ctx, ScheduleJob job) {
        LocalDateTime nextTime = this.calculateNextTime(ctx, job);
        job.setTriggerNextTime(nextTime);
    }

    private LocalDateTime calculateNextTime(ScheduleContext ctx, ScheduleJob job) {
        LocalDateTime firstTime = job.now().plusSeconds(ctx.initDelay());

        this.report(ctx, job, firstTime);

        return firstTime;
    }

    private void report(ScheduleContext ctx, ScheduleJob job, LocalDateTime nextTime) {
        if (log.isInfoEnabled()) {
            log.info("riff: Attempting to calculate next trigger time for fixed-rate job [{}]. Now: [{}], Next: [{}]",
                ctx.query(),
                LocalDateTimes.format(job.now()),
                LocalDateTimes.format(nextTime)
            );
        }
    }
}
