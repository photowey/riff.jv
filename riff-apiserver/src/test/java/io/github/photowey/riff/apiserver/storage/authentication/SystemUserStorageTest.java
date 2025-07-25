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
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import io.github.photowey.riff.apiserver.AbstractLocalTest;
import io.github.photowey.riff.apiserver.TestApiServer;
import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.core.domain.query.SystemUserQuery;
import io.github.photowey.riff.core.domain.query.pagination.SystemUserPaginationQuery;
import io.github.photowey.riff.infras.authentication.core.enums.AuthenticationDictionary;
import io.github.photowey.riff.infras.model.result.PageResult;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code SystemUserStorageTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
@SpringBootTest(classes = TestApiServer.class)
//@TestPropertySource(properties = "spring.datasource.access.enabled=true")
@EnabledIf(expression = "${spring.datasource.access.enabled}", loadContext = true)
class SystemUserStorageTest extends AbstractLocalTest {

    //
    // DATABASE_MYSQL_ADDRESS=127.0.0.1:3307;DATABASE_MYSQL_DATABASE=riff;DATABASE_MYSQL_USERNAME=root;\
    // DATABASE_MYSQL_PASSWORD=aZI0cNjQ1lJ6BUnTgapnMHjGA7l1SuNA;SPRING_SECURITY_USER_NAME=admin;\
    // SPRING_SECURITY_USER_PASSWORD=admin;spring.datasource.access.enabled=true
    //

    @Test
    @Rollback
    void testSave() {
        SystemUser systemUser = SystemUser.builder()
            .username("admin")
            .password("admin@riff.jv")
            .email("photowey@gmail.com")
            .mobile("18888888888")
            .twofaEnabled(0)
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
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
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .build();

        SystemUser systemUser01 = SystemUser.builder()
            .username("admin01")
            .password("admin@riff.jv")
            .email("photowey@gmail.com")
            .mobile("18888888888")
            .twofaEnabled(0)
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .build();

        this.systemUserStorage.batchSave(List.of(systemUser00, systemUser01));

        Assertions.assertNotNull(systemUser00.id());
        Assertions.assertNotNull(systemUser01.id());

        SystemUser selected00 = this.systemUserStorage.selectOne(systemUser00.id());
        Assertions.assertNotNull(selected00);

        SystemUser selected01 = this.systemUserStorage.selectOne(systemUser01.id());
        Assertions.assertNotNull(selected01);
    }

    @Test
    void testSelectList() {
        SystemUserQuery query = SystemUserQuery.builder()
            .id(1L)
            .username("admin")
            .mobile("18888888888")
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .build();

        List<SystemUser> systemUsers = this.systemUserStorage.selectList(query);
        Assertions.assertNotNull(systemUsers);
        Assertions.assertEquals(0, systemUsers.size());
    }

    @Test
    void testSelectPage() {
        SystemUserPaginationQuery query = SystemUserPaginationQuery.builder()
            .id(0L)
            .username("admin")
            .mobile("18888888888")
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .build();

        PageResult<SystemUser> result = this.systemUserStorage.selectPage(query);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.data().total());
    }

    //@Test
    void testTryFindByUsername() {
        Optional<SystemUser> systemUserOpt = this.systemUserStorage.tryFindSystemUser("admin");
        Assertions.assertTrue(systemUserOpt.isPresent());
    }
}
