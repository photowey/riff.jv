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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Base64s}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public final class Base64s {

    private Base64s() {
        AssertionErrors.throwz(Base64s.class);
    }

    public static byte[] encrypt(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    public static byte[] encrypt(String data) {
        return encrypt(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String encryptBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static String encryptBase64(String data) {
        return encryptBase64(data.getBytes(StandardCharsets.UTF_8));
    }

    // ----------------------------------------------------------------

    public static byte[] decrypt(byte[] data) {
        return Base64.getDecoder().decode(data);
    }

    public static byte[] decrypt(String base64) {
        return decrypt(base64.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptBase64(byte[] encrypted) {
        return new String(decrypt(encrypted), StandardCharsets.UTF_8);
    }

    public static String decryptBase64(String encrypted) {
        return decryptBase64(encrypted.getBytes(StandardCharsets.UTF_8));
    }
}
