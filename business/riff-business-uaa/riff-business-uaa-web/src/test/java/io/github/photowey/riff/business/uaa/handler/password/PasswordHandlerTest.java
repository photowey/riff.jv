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
package io.github.photowey.riff.business.uaa.handler.password;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import io.github.photowey.riff.business.uaa.AbstractLocalTest;
import io.github.photowey.riff.business.uaa.TestUaa;

import lombok.extern.slf4j.Slf4j;


/**
 * {@code PasswordHandlerTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Slf4j
@SpringBootTest(classes = TestUaa.class)
//@TestPropertySource(properties = "ci.spring.datasource.access.enabled=true")
@EnabledIf(expression = "${ci.spring.datasource.access.enabled}", loadContext = true)
class PasswordHandlerTest extends AbstractLocalTest {

    @Test
    void testPasswordEncode() {
        String password = "admin@riff.jv";
        String encoded = this.passwordHandler.encode(password);
        log.info("the encoded password is:[{},{}]", password, encoded);

        boolean matches = this.passwordHandler.matches(password, encoded);
        Assertions.assertTrue(matches);
    }
}
