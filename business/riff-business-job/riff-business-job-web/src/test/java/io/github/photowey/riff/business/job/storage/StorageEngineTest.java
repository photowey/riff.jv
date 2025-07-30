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

import io.github.photowey.riff.business.job.AbstractLocalTest;
import io.github.photowey.riff.business.job.TestJob;
import io.github.photowey.riff.core.domain.entity.SystemUser;

/**
 * {@code StorageEngineTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/30
 */
@SpringBootTest(classes = TestJob.class)
//@TestPropertySource(properties = "ci.spring.datasource.access.enabled=true")
@EnabledIf(expression = "${ci.spring.datasource.access.enabled}", loadContext = true)
class StorageEngineTest extends AbstractLocalTest {

    @Test
    void testStorageBean() {
        SystemUser notFound = this.storageEngine.systemUserStorage().selectOne(0L);
        Assertions.assertNull(notFound);
    }
}
