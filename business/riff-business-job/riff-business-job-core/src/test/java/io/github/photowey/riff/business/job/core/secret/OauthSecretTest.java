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
package io.github.photowey.riff.business.job.core.secret;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.infras.crypto.integrated.Cryptos;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code OauthSecretTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Slf4j
class OauthSecretTest {

    @Test
    void testAccessKey() {
        String today = "250808";
        String accessKey = OauthSecret.genAccessKey();

        Assertions.assertEquals(32, accessKey.length());
        Assertions.assertTrue(accessKey.startsWith(today));
    }

    @Test
    void testAccessSecret() {
        String accessKey = OauthSecret.genAccessKey();
        String accessSecret = OauthSecret.genAccessSecret(accessKey);

        Assertions.assertEquals(64, accessSecret.length());
        Assertions.assertTrue(accessSecret.endsWith(Cryptos.HASH.md5(accessKey).substring(0, 8)));
    }
}
