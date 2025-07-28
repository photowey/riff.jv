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
package io.github.photowey.riff.business.job;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import io.github.photowey.riff.infras.authentication.core.util.Requests;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.storage.api.SystemUserStorage;

/**
 * {@code AbstractLocalTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
public abstract class AbstractLocalTest {

    @Autowired
    protected SystemUserStorage<SystemUserPO> systemUserStorage;

    protected void tryHttpRequestTest(Runnable task) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Tenant", "saas");
        request.addHeader("X-Platform", "saas");
        request.addHeader("X-App", "boss");

        try {
            Requests.resetRequest(request);
            task.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Requests.cleanRequest();
        }
    }

    protected void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sleep(long sleepTimes, TimeUnit unit) {
        this.sleep(unit.toMillis(sleepTimes));
    }
}
