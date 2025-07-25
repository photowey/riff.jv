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
package io.github.photowey.riff.apiserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import io.github.photowey.riff.apiserver.AbstractLocalTest;
import io.github.photowey.riff.apiserver.TestApiServer;
import io.github.photowey.riff.business.uaa.core.domain.dto.TokenDTO;
import io.github.photowey.riff.business.uaa.core.domain.payload.LoginPayload;
import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.common.json.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code LoginServiceTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/25
 */
@Slf4j
@SpringBootTest(classes = TestApiServer.class)
//@TestPropertySource(properties = "spring.datasource.access.enabled=true")
@EnabledIf(expression = "${spring.datasource.access.enabled}", loadContext = true)
class LoginServiceTest extends AbstractLocalTest {

    @Test
    void testLogin() {
        LoginPayload payload = LoginPayload.builder()
            .username("admin")
            .password("admin@riff.jv")
            .rememberMe(1)
            .captchaId("70202507155719188245190111831234")
            .captcha("Az88")
            .type(1)
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .client("web")
            .protocol(AuthorityConstants.ACCOUNT_PROTOCOL)
            .build();

        this.tryHttpRequestTest(() -> {
            TokenDTO token = this.loginService.login(payload);
            log.info("the login token is:[{}]", JSON.toPrettyString(token));
        });

        this.sleep(3_000L);
    }
}
