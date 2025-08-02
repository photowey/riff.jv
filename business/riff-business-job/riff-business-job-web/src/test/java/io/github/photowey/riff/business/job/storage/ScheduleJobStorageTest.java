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
package io.github.photowey.riff.business.job.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.AbstractLocalTest;
import io.github.photowey.riff.business.job.TestJob;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;

/**
 * {@code ScheduleJobStorageTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/31
 */
@SpringBootTest(classes = TestJob.class)
//@TestPropertySource(properties = "ci.spring.datasource.access.enabled=true")
@EnabledIf(expression = "${ci.spring.datasource.access.enabled}", loadContext = true)
class ScheduleJobStorageTest extends AbstractLocalTest {

    @Test
    @Transactional
    void testSave_dummy() {
        Long appId = 1950590661197238273L;
        ScheduleJob scheduleJob = ScheduleJob.builder()
            .appId(appId)
            .jobCode("io.github.photowey.riff.order.timeout.close")
            .jobName("Payment not completed in time, order closed")
            .jobType(1)
            .handlerName("orderTimeoutHandler")
            .declaredClass("io.github.photowey.riff.order.timeout.close.OrderTimeoutHandler")
            .method("hande")
            .arguments("[]")
            .scheduleType(1)
            .scheduleContext("riff://trigger/cron?expression=0%2F5+*+*+*+*+%3F&initialDelay=0&delay=0")
            .misfireStrategy(1)
            .routeStrategy(1)
            .blockStrategy(1)
            .timeoutSeconds(5)
            .retryCount(5)
            .build();

        ScheduleApp notNullApp = this.storageEngine.scheduleAppStorage().selectOne(appId);
        Assertions.assertNotNull(notNullApp);

        this.storageEngine.scheduleJobStorage().save(scheduleJob);

        ScheduleJob notNull = this.storageEngine.scheduleJobStorage().selectOne(scheduleJob.id());
        Assertions.assertNotNull(notNull);
    }
}
