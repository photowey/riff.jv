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
package io.github.photowey.riff.infras.crypto.algo.aes;

import io.github.photowey.riff.infras.crypto.AbstractCryptoTest;
import io.github.photowey.riff.infras.crypto.algo.hash.Hashes;
import io.github.photowey.riff.infras.crypto.integrated.Cryptos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * {@code AESsTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
class AESsTest extends AbstractCryptoTest {

    @BeforeAll
    static void init() {
        Cryptos.Hammer.addProviders();
    }

    @Test
    void testSHA1PRNG() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_DATA;

        String encrypted = AESs.SHA1PRNG.encrypt(key, data);
        String decrypted = AESs.SHA1PRNG.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testSHA1PRNG_chinese() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.SHA1PRNG.encrypt(key, data);
        String decrypted = AESs.SHA1PRNG.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_no_padding() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_DATA;

        String encrypted = AESs.CBC.NoPadding.encrypt(key, data);
        String decrypted = AESs.CBC.NoPadding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_no_padding_chinese() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.CBC.NoPadding.encrypt(key, data);
        String decrypted = AESs.CBC.NoPadding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_no_padding_iv() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String iv = Hashes.MD5.md5("io.github.photowey.iv");
        String data = TEST_DATA;

        String encrypted = AESs.CBC.NoPadding.encrypt(key, data, iv.substring(16));
        String decrypted = AESs.CBC.NoPadding.decrypt(
            key, encrypted, iv.substring(16));

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_no_padding_iv_chinese() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String iv = Hashes.MD5.md5("io.github.photowey.iv");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.CBC.NoPadding.encrypt(key, data, iv.substring(16));
        String decrypted = AESs.CBC.NoPadding.decrypt(
            key, encrypted, iv.substring(16));

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_pkcs5_padding() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_DATA;

        String encrypted = AESs.CBC.PKCS5Padding.encrypt(key, data);
        String decrypted = AESs.CBC.PKCS5Padding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_pkcs5_padding_chinese() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.CBC.PKCS5Padding.encrypt(key, data);
        String decrypted = AESs.CBC.PKCS5Padding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_pkcs5_padding_iv() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String iv = Hashes.MD5.md5("io.github.photowey.iv");
        String data = TEST_DATA;

        String encrypted = AESs.CBC.PKCS5Padding
            .encrypt(key, data, iv.substring(16));
        String decrypted = AESs.CBC.PKCS5Padding
            .decrypt(key, encrypted, iv.substring(16));

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_pkcs5_padding_iv_chinese() {
        String key = Hashes.MD5.md5("io.github.photowey");
        String iv = Hashes.MD5.md5("io.github.photowey.iv");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.CBC.PKCS5Padding
            .encrypt(key, data, iv.substring(16));
        String decrypted = AESs.CBC.PKCS5Padding
            .decrypt(key, encrypted, iv.substring(16));

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_PKCS7Padding() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = TEST_DATA;

        String encrypted = AESs.CBC.PKCS7Padding.encrypt(key, data);
        String decrypted = AESs.CBC.PKCS7Padding.decrypt(key, encrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testCBC_PKCS7Padding_chinese() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = TEST_CHINESE_DATA;

        String encrypted = AESs.CBC.PKCS7Padding.encrypt(key, data);
        String decrypted = AESs.CBC.PKCS7Padding.decrypt(key, encrypted);
        Assertions.assertEquals(data, decrypted);
    }

    // ----------------------------------------------------------------

    @Test
    void testECB_NoPadding() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = Hashes.MD5.md5(TEST_DATA);

        String encrypted = AESs.ECB.NoPadding.encrypt(key, data);
        String decrypted = AESs.ECB.NoPadding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testECB_NoPadding_chinese() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = Hashes.MD5.md5(TEST_CHINESE_DATA);

        String encrypted = AESs.ECB.NoPadding.encrypt(key, data);
        String decrypted = AESs.ECB.NoPadding.decrypt(key, encrypted);

        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testECB_PKCS5Padding() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = TEST_DATA;

        String encrypted = AESs.ECB.PKCS5Padding.encrypt(key, data);
        String decrypted = AESs.ECB.PKCS5Padding.decrypt(key, encrypted);
        Assertions.assertEquals(data, decrypted);
    }

    @Test
    void testECB_PKCS7Padding() {
        String key = Hashes.MD5.md5("0123456789876543210");
        String data = TEST_DATA;

        String encrypted = AESs.ECB.PKCS7Padding.encrypt(key, data);
        String decrypted = AESs.ECB.PKCS7Padding.decrypt(key, encrypted);
        Assertions.assertEquals(data, decrypted);
    }
}
