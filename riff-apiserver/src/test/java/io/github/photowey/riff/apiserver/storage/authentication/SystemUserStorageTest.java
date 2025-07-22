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
package io.github.photowey.riff.apiserver.storage.authentication;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;

import io.github.photowey.riff.apiserver.TestApiServer;
import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.storage.api.SystemUserStorage;

/**
 * {@code SystemUserStorageTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@SpringBootTest(classes = {
    TestApiServer.class,
})
@Profile(value = "local")
class SystemUserStorageTest {

    @Autowired
    private SystemUserStorage<SystemUserPO> systemUserStorage;

    @Test
    @Rollback
    void testSave() {
        SystemUser systemUser = SystemUser.builder()
            .username("admin")
            .password("admin@riff.jv")
            .email("photowey@gmail.com")
            .mobile("18888888888")
            .twofaEnabled(0)
            .build();

        this.systemUserStorage.save(systemUser);

        Long userId = systemUser.id();
        Assertions.assertNotNull(userId);

        SystemUser selected = this.systemUserStorage.selectOne(userId);
        Assertions.assertNotNull(selected);
    }

    @Test
    @Rollback
    void testBatchSave() {
        SystemUser systemUser00 = SystemUser.builder()
            .username("admin00")
            .password("admin@riff.jv")
            .email("photowey@gmail.com")
            .mobile("18888888888")
            .twofaEnabled(0)
            .build();

        SystemUser systemUser01 = SystemUser.builder()
            .username("admin01")
            .password("admin@riff.jv")
            .email("photowey@gmail.com")
            .mobile("18888888888")
            .twofaEnabled(0)
            .build();

        this.systemUserStorage.batchSave(List.of(systemUser00, systemUser01));

        Assertions.assertNotNull(systemUser00.id());
        Assertions.assertNotNull(systemUser01.id());

        SystemUser selected00 = this.systemUserStorage.selectOne(systemUser00.id());
        Assertions.assertNotNull(selected00);

        SystemUser selected01 = this.systemUserStorage.selectOne(systemUser01.id());
        Assertions.assertNotNull(selected01);
    }
}
