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
package io.github.photowey.riff.infras.crypto.algo.codec.base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.infras.crypto.AbstractCryptoTest;

/**
 * {@code Base64sTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
class Base64sTest extends AbstractCryptoTest {

    @Test
    void testBase64() {
        String data = TEST_DATA;

        String encrypted = Base64s.encryptBase64(data);
        String decrypted = Base64s.decryptBase64(encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testBase64_chinese() {
        String data = TEST_CHINESE_DATA;

        String encrypted = Base64s.encryptBase64(data);
        String decrypted = Base64s.decryptBase64(encrypted);

        Assertions.assertEquals(data, decrypted);
    }

}
