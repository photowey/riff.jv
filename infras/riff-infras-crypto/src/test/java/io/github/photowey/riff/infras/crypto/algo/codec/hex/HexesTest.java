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
package io.github.photowey.riff.infras.crypto.algo.codec.hex;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.infras.crypto.AbstractCryptoTest;

/**
 * {@code HexesTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
class HexesTest extends AbstractCryptoTest {

    @Test
    void testHex() {
        String data = TEST_DATA;

        String hex = Hexes.toHex(data);
        String original = Hexes.toOriginal(hex);

        Assertions.assertEquals(data, original);

        String hex1 = Hexes.toHex(data.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = Hexes.toBytes(hex1);

        Assertions.assertEquals(data, new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    void testHex_chinese() {
        String data = TEST_CHINESE_DATA;

        String hex = Hexes.toHex(data);
        String original = Hexes.toOriginal(hex);

        Assertions.assertEquals(data, original);

        String hex1 = Hexes.toHex(data.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = Hexes.toBytes(hex1);

        Assertions.assertEquals(data, new String(bytes, StandardCharsets.UTF_8));
    }
}
