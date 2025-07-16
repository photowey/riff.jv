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
package io.github.photowey.riff.infras.authentication.core.domain.authenticated;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * {@code LoginUserDefaultPasswordTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Slf4j
class LoginUserDefaultPasswordTest {

    private static final String DEFAULT_PASSWORD = "admin.d@riff.jv";

    @Test
    void testDefaultPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encoded = passwordEncoder.encode(DEFAULT_PASSWORD);
        Assertions.assertTrue(passwordEncoder.matches(DEFAULT_PASSWORD, encoded));
    }
}
