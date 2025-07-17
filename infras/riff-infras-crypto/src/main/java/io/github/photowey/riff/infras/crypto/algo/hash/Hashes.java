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
package io.github.photowey.riff.infras.crypto.algo.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.crypto.algo.codec.hex.Hexes;

/**
 * {@code Hashes}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public final class Hashes {

    private Hashes() {
        AssertionErrors.throwz(Hashes.class);
    }

    public static final class SHA1 {

        private SHA1() {
            AssertionErrors.throwz(SHA1.class);
        }

        public static final String ALGORITHM_SHA1 = "SHA-1";

        public static String sha1(String data) {
            return hash(ALGORITHM_SHA1, data);
        }
    }

    public static final class SHA256 {

        private SHA256() {
            AssertionErrors.throwz(SHA256.class);
        }

        public static final String ALGORITHM_SHA256 = "SHA-256";

        public static String sha256(String data) {
            return hash(ALGORITHM_SHA256, data);
        }
    }

    public static final class SHA384 {

        private SHA384() {
            AssertionErrors.throwz(SHA384.class);
        }

        public static final String ALGORITHM_SHA384 = "SHA-384";

        public static String sha384(String data) {
            return hash(ALGORITHM_SHA384, data);
        }
    }

    public static final class SHA512 {

        private SHA512() {
            AssertionErrors.throwz(SHA512.class);
        }

        public static final String ALGORITHM_SHA512 = "SHA-512";

        public static String sha512(String data) {
            return hash(ALGORITHM_SHA512, data);
        }
    }

    public static final class MD5 {

        private MD5() {
            AssertionErrors.throwz(MD5.class);
        }

        public static final String ALGORITHM_MD5 = "MD5";

        public static String md5(String data) {
            return hash(ALGORITHM_MD5, data);
        }
    }

    public static String hash(String algorithm, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            return Hexes.toHex(bytes);
        } catch (Exception e) {
            throw new SecurityException("HASH: handle hash crypto failed", e);
        }
    }
}
