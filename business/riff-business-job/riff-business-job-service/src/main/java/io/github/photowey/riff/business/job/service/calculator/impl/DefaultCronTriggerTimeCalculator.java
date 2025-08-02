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

import org.springframework.stereotype.Component;

import io.github.photowey.riff.business.job.core.context.ScheduleContext;
import io.github.photowey.riff.business.job.service.calculator.CronTriggerTimeCalculator;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

/**
 * {@code DefaultCronTriggerTimeCalculator}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/01
 */
@Component
public class DefaultCronTriggerTimeCalculator
    extends AbstractBeanFactoryHolder implements CronTriggerTimeCalculator {

    @Override
    public void handle(ScheduleContext ctx, ScheduleJob job) {

    }
}
