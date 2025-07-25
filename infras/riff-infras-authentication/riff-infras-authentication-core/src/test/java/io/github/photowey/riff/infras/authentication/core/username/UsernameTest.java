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
package io.github.photowey.riff.infras.authentication.core.username;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;

/**
 * {@code UsernameTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/25
 */
class UsernameTest {

    @Test
    void testCompact() {
        Username username = Username.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .client("web")
            .type(1)
            .username("admin")
            .rememberMe(1)
            .build();

        String compacted = username.compact();
        Assertions.assertEquals("admin?tenant=saas&platform=saas&app=boss&client=web&type=1&rememberMe=1", compacted);

        Username parsed = Username.parse(compacted);

        Assertions.assertEquals(username.tenant(), parsed.tenant());
        Assertions.assertEquals(username.platform(), parsed.platform());
        Assertions.assertEquals(username.app(), parsed.app());
        Assertions.assertEquals(username.client(), parsed.client());
        Assertions.assertEquals(username.type(), parsed.type());
        Assertions.assertEquals(username.username(), parsed.username());
        Assertions.assertEquals(username.rememberMe(), parsed.rememberMe());
    }

    @Test
    void testCompact_bad() {
        Assertions.assertThrows(SecurityAuthenticationException.class, () -> {
            Username.parse("admin?tenant=saas&platform=saas&app=boss&client=web&type=1&");
        });
    }
}
