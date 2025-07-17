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
package io.github.photowey.riff.infras.crypto.integrated;

import java.io.Serializable;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.Callable;

import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.crypto.algo.aes.AESs;
import io.github.photowey.riff.infras.crypto.algo.hash.Hashes;
import io.github.photowey.riff.infras.crypto.core.runner.SneakyRunners;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * {@code Cryptos}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
@SuppressWarnings("all")
public final class Cryptos {

    private Cryptos() {
        AssertionErrors.throwz(Cryptos.class);
    }

    public static final class Hammer {

        private Hammer() {
            AssertionErrors.throwz(Hammer.class);
        }

        public static void addProviders() {
            Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
            if (Objects.isNull(provider)) {
                Security.addProvider(new BouncyCastleProvider());
            }
        }
    }

    public static final class HASH implements Serializable {

        private HASH() {
            AssertionErrors.throwz(HASH.class);
        }

        public static String md5(String data) {
            return Hashes.MD5.md5(data);
        }

        public static String sha1(String data) {
            return Hashes.SHA1.sha1(data);
        }

        public static String sha256(String data) {
            return Hashes.SHA256.sha256(data);
        }

        public static String sha384(String data) {
            return Hashes.SHA384.sha384(data);
        }

        public static String sha512(String data) {
            return Hashes.SHA512.sha512(data);
        }
    }

    public final static class AES {

        private AES() {
            AssertionErrors.throwz(AES.class);
        }

        public static final class SHA1PRNG {

            public static String encrypt(String key, String data) {
                return AESs.SHA1PRNG.encrypt(key, data);
            }

            public static String decrypt(String key, String data) {
                return AESs.SHA1PRNG.decrypt(key, data);
            }
        }

        public static final class CBC {

            private CBC() {
                AssertionErrors.throwz(CBC.class);
            }

            public static final class NoPadding {

                private NoPadding() {
                    AssertionErrors.throwz(NoPadding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.CBC.NoPadding.encrypt(key, data);
                }

                public static String encrypt(String key, String data, String iv) {
                    return AESs.CBC.NoPadding.encrypt(key, data, iv);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.CBC.NoPadding.decrypt(key, encrypted);
                }

                public static String decrypt(String key, String encrypted, String iv) {
                    return AESs.CBC.NoPadding.decrypt(key, encrypted, iv);
                }
            }

            public static final class PKCS5Padding {

                private PKCS5Padding() {
                    AssertionErrors.throwz(PKCS5Padding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.CBC.PKCS5Padding.encrypt(key, data);
                }

                public static String encrypt(String key, String data, String iv) {
                    return AESs.CBC.PKCS5Padding.encrypt(key, data, iv);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.CBC.PKCS5Padding.decrypt(key, encrypted);
                }

                public static String decrypt(String key, String encrypted, String iv) {
                    return AESs.CBC.PKCS5Padding.decrypt(key, encrypted, iv);
                }
            }

            public static final class PKCS7Padding {

                private PKCS7Padding() {
                    AssertionErrors.throwz(PKCS7Padding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.CBC.PKCS7Padding.encrypt(key, data);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.CBC.PKCS7Padding.decrypt(key, encrypted);
                }
            }
        }

        public static final class ECB {

            private ECB() {
                AssertionErrors.throwz(ECB.class);
            }

            public static final class NoPadding implements Serializable {

                private NoPadding() {
                    AssertionErrors.throwz(NoPadding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.ECB.NoPadding.encrypt(key, data);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.ECB.NoPadding.decrypt(key, encrypted);
                }
            }

            public static final class PKCS5Padding implements Serializable {

                private PKCS5Padding() {
                    AssertionErrors.throwz(PKCS5Padding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.ECB.PKCS5Padding.encrypt(key, data);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.ECB.PKCS5Padding.decrypt(key, encrypted);
                }
            }

            public static final class PKCS7Padding implements Serializable {

                private PKCS7Padding() {
                    AssertionErrors.throwz(PKCS7Padding.class);
                }

                public static String encrypt(String key, String data) {
                    return AESs.ECB.PKCS7Padding.encrypt(key, data);
                }

                public static String decrypt(String key, String encrypted) {
                    return AESs.ECB.PKCS7Padding.decrypt(key, encrypted);
                }
            }
        }
    }

    public static <T> T call(Callable<T> task, String message) {
        return SneakyRunners.tryCall(task, message);
    }

    public static <T> T throwUnchecked(Throwable e, String message, Object... args) {
        throw new SecurityException(StringFormatter.format(message, args), e);
    }
}
