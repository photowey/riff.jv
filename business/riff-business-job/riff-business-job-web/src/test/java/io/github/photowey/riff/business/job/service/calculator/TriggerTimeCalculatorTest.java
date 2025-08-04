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
package io.github.photowey.riff.business.job.service.calculator;

import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.business.job.core.context.ScheduleContext;
import io.github.photowey.riff.business.job.service.calculator.impl.DefaultCronTriggerTimeCalculator;
import io.github.photowey.riff.business.job.service.calculator.impl.DefaultFixedRateTriggerTimeCalculator;
import io.github.photowey.riff.business.job.service.calculator.impl.DefaultScheduleOnceTriggerTimeCalculator;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.enums.RiffDictionary;
import io.github.photowey.riff.infras.common.constant.datetime.DatePatternConstants;
import io.github.photowey.riff.infras.common.datetime.LocalDateTimes;

/**
 * {@code TriggerTimeCalculatorTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/04
 */
class TriggerTimeCalculatorTest {

    @Test
    void testScheduleOnceTriggerTimeCalculator() {
        ScheduleContext initCtx = ScheduleContext.once(10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultScheduleOnceTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(10L), job.triggerNextTime());
    }

    @Test
    void testCronTriggerTimeCalculator() {
        ScheduleContext initCtx = ScheduleContext.cron("0/10 * * * * ?");

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultCronTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(10L), job.triggerNextTime());
    }

    @Test
    void testCronTriggerTimeCalculator_init_delay() {
        ScheduleContext initCtx = ScheduleContext.cron("0/10 * * * * ?", 10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultCronTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(20L), job.triggerNextTime());
    }

    @Test
    void testFixedRateTriggerTimeCalculator() {
        ScheduleContext initCtx = ScheduleContext.fixedRate(10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultFixedRateTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(0L), job.triggerNextTime());
    }

    @Test
    void testFixedRateTriggerTimeCalculator_init_delay() {
        ScheduleContext initCtx = ScheduleContext.fixedRate(20L, 10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultFixedRateTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(20L), job.triggerNextTime());
    }

    @Test
    void testFixedDelayTriggerTimeCalculator() {
        ScheduleContext initCtx = ScheduleContext.fixedDelay(10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultFixedRateTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(0L), job.triggerNextTime());
    }

    @Test
    void testFixedDelayTriggerTimeCalculator_init_delay() {
        ScheduleContext initCtx = ScheduleContext.fixedDelay(20L, 10L);

        ScheduleJob job = new ScheduleJob();
        job.setScheduleType(RiffDictionary.Schedule.Type.SCHEDULE_ONCE.value());
        job.setScheduleContext(initCtx.compact());

        String now = "2025-08-04 15:55:00";
        LocalDateTime baseTime = LocalDateTimes.toLocalDateTime(now, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
        job.setNow(baseTime);

        ScheduleContext ctx = ScheduleContext.parse(job.scheduleContext());

        TriggerTimeCalculator calculator = new DefaultFixedRateTriggerTimeCalculator();
        calculator.handle(ctx, job);

        Assertions.assertEquals(Objects.requireNonNull(baseTime).plusSeconds(20L), job.triggerNextTime());
    }
}
