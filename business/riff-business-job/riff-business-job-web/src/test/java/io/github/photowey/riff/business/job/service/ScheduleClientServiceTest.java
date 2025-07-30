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
package io.github.photowey.riff.business.job.service;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.AbstractLocalTest;
import io.github.photowey.riff.business.job.TestJob;
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleClientAddPayload;
import io.github.photowey.riff.core.domain.entity.ScheduleClient;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;

/**
 * {@code ScheduleClientServiceTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/31
 */
@SpringBootTest(classes = TestJob.class)
//@TestPropertySource(properties = "ci.spring.datasource.access.enabled=true")
@EnabledIf(expression = "${ci.spring.datasource.access.enabled}", loadContext = true)
class ScheduleClientServiceTest extends AbstractLocalTest {

    //@Test
    @Transactional
    void testSave() {
        Long appId = 1950590661197238273L;
        Long jobId = 1950590661197238274L;
        ScheduleClientAddPayload payload = ScheduleClientAddPayload.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .jobId(jobId)
            .serverIp("192.168.0.101")
            .serverPort(9988)
            .serverProtocol("http")
            // 优先
            //.serverAddress("http://192.168.0.101:9988")
            .build();

        LoginUserHolder.mock(appId, () -> {
            ScheduleClient client = this.scheduleClientService.register(payload);
            Assertions.assertNotNull(client);
        }, true);
    }
}
