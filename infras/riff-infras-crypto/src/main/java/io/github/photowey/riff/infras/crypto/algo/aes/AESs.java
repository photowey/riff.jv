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

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.crypto.algo.codec.base64.Base64s;
import io.github.photowey.riff.infras.crypto.algo.codec.hex.Hexes;
import io.github.photowey.riff.infras.crypto.integrated.Cryptos;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * {@code AESs}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
@SuppressWarnings("all")
public final class AESs {

    public static final String ALGORITHM_AES = "AES";
    public static final String SECURE_RANDOM_SHA1PRNG = "SHA1PRNG";

    public static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";
    public static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    public static final String AES_CBC_PKCS7_PADDING = "AES/CBC/PKCS7Padding";

    public static final String AES_ECB_NO_PADDING = "AES/ECB/NoPadding";
    public static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";
    public static final String AES_ECB_PKCS7_PADDING = "AES/ECB/PKCS7Padding";

    public static final int KEY_INIT_LENGTH = 128;
    private static final int INIT_VECTOR_LENGTH = 16;

    private AESs() {
        AssertionErrors.throwz(AESs.class);
    }

    public static final class SHA1PRNG {

        private SHA1PRNG() {
            AssertionErrors.throwz(SHA1PRNG.class);
        }

        public static String encrypt(String key, String data) {
            return Cryptos.call(() -> {
                KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM_AES);
                SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_SHA1PRNG);
                random.setSeed(key.getBytes(StandardCharsets.UTF_8));
                keyGen.init(KEY_INIT_LENGTH, random);

                SecretKey originalKey = keyGen.generateKey();
                byte[] raw = originalKey.getEncoded();
                SecretKey secretKey = new SecretKeySpec(raw, ALGORITHM_AES);
                Cipher cipherInstance = Cipher.getInstance(ALGORITHM_AES);
                cipherInstance.init(Cipher.ENCRYPT_MODE, secretKey);

                byte[] byteEncode = data.getBytes(StandardCharsets.UTF_8);
                byte[] byteAes = cipherInstance.doFinal(byteEncode);

                return Base64s.encryptBase64(byteAes);
            }, "AES: handle AES SHA1PRNG encrypt failed");
        }

        public static String decrypt(String key, String data) {
            return Cryptos.call(() -> {
                KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM_AES);
                SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_SHA1PRNG);
                random.setSeed(key.getBytes(StandardCharsets.UTF_8));
                keyGen.init(KEY_INIT_LENGTH, random);

                SecretKey originalKey = keyGen.generateKey();
                byte[] raw = originalKey.getEncoded();

                SecretKey rules = new SecretKeySpec(raw, ALGORITHM_AES);
                Cipher cipherInstance = Cipher.getInstance(ALGORITHM_AES);
                cipherInstance.init(Cipher.DECRYPT_MODE, rules);

                byte[] contentByte = Base64s.decrypt(data);
                byte[] decodeByte = cipherInstance.doFinal(contentByte);

                return new String(decodeByte, StandardCharsets.UTF_8);
            }, "AES: handle AES SHA1PRNG decrypt failed");
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
                return Cryptos.call(() -> {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] ivBytes = new byte[INIT_VECTOR_LENGTH / 2];
                    secureRandom.nextBytes(ivBytes);
                    String iv = Hexes.toHex(ivBytes);
                    ivBytes = iv.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_NO_PADDING);
                    cipherInstance.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                    int blockSize = cipherInstance.getBlockSize();
                    byte[] dataBytes = padIfNecessary(data, blockSize);
                    byte[] encrypted = cipherInstance.doFinal(dataBytes);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(
                        ivBytes.length + encrypted.length);
                    byteBuffer.put(ivBytes);
                    byteBuffer.put(encrypted);

                    return Base64s.encryptBase64(byteBuffer.array());
                }, "AES: handle AES/CBC/NoPadding encrypt failed");
            }

            public static String encrypt(String key, String data, String iv) {
                byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

                return Cryptos.call(() -> {
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_NO_PADDING);
                    cipherInstance.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                    int blockSize = cipherInstance.getBlockSize();
                    byte[] dataBytes = padIfNecessary(data, blockSize);
                    byte[] encrypted = cipherInstance.doFinal(dataBytes);

                    return Base64s.encryptBase64(encrypted);
                }, "AES: handle AES/CBC/NoPadding encrypt failed");
            }

            // ----------------------------------------------------------------

            public static String decrypt(String key, String encrypted) {
                return Cryptos.call(() -> {
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    byte[] encryptedBytes = Base64s.decrypt(encrypted);
                    IvParameterSpec ivSpec = new IvParameterSpec(
                        encryptedBytes, 0, INIT_VECTOR_LENGTH
                    );

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_NO_PADDING);
                    cipherInstance.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] decrypted = cipherInstance.doFinal(
                        encryptedBytes, INIT_VECTOR_LENGTH,
                        encryptedBytes.length - INIT_VECTOR_LENGTH
                    );

                    return new String(decrypted, StandardCharsets.UTF_8).trim();
                }, "AES: handle AES/CBC/NoPadding decrypt failed");
            }

            public static String decrypt(String key, String encrypted, String iv) {
                return Cryptos.call(() -> {
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
                    byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_NO_PADDING);
                    cipherInstance.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] dataBytes = Base64s.decrypt(encrypted);
                    byte[] decrypted = cipherInstance.doFinal(dataBytes);

                    return new String(decrypted, StandardCharsets.UTF_8).trim();
                }, "AES: handle AES/CBC/NoPadding decrypt failed");
            }
        }

        public static final class PKCS5Padding {

            private PKCS5Padding() {
                AssertionErrors.throwz(PKCS5Padding.class);
            }

            // ----------------------------------------------------------------

            public static String encrypt(String key, String data) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] ivBytes = new byte[INIT_VECTOR_LENGTH / 2];
                    secureRandom.nextBytes(ivBytes);
                    String iv = Hexes.toHex(ivBytes);
                    ivBytes = iv.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
                    cipherInstance.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                    byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                    byte[] encrypted = cipherInstance.doFinal(dataBytes);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(
                        ivBytes.length + encrypted.length
                    );
                    byteBuffer.put(ivBytes);
                    byteBuffer.put(encrypted);

                    return Base64s.encryptBase64(byteBuffer.array());
                }, "AES: handle aes.PKCS5Padding encrypt failed");
            }

            public static String encrypt(String key, String data, String iv) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);
                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
                    cipherInstance.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

                    byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                    byte[] encrypted = cipherInstance.doFinal(dataBytes);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(
                        ivBytes.length + encrypted.length);
                    byteBuffer.put(ivBytes);
                    byteBuffer.put(encrypted);

                    return Base64s.encryptBase64(byteBuffer.array());
                }, "AES: handle AES/CBC/PKCS5Padding encrypt failed");
            }

            // ----------------------------------------------------------------

            public static String decrypt(String key, String encrypted) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    byte[] encryptedBytes = Base64s.decrypt(encrypted);

                    IvParameterSpec ivSpec =
                        new IvParameterSpec(encryptedBytes, 0, INIT_VECTOR_LENGTH);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
                    cipherInstance.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] decrypted = cipherInstance.doFinal(
                        encryptedBytes, INIT_VECTOR_LENGTH,
                        encryptedBytes.length - INIT_VECTOR_LENGTH
                    );

                    return new String(decrypted, StandardCharsets.UTF_8);
                }, "AES: handle AES/CBC/PKCS5Padding decrypt failed");
            }

            public static String decrypt(String key, String encrypted, String iv) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipherInstance = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
                    cipherInstance.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] dataBytes = Base64s.decrypt(encrypted);
                    byte[] decrypted = cipherInstance.doFinal(
                        dataBytes, INIT_VECTOR_LENGTH,
                        dataBytes.length - INIT_VECTOR_LENGTH
                    );

                    return new String(decrypted, StandardCharsets.UTF_8);
                }, "AES: handle AES/CBC/PKCS5Padding decrypt failed");
            }
        }

        public static final class PKCS7Padding implements Serializable {

            private static final int PKCS7PADDING_INIT_VECTOR_LENGTH = 16;

            private PKCS7Padding() {
                AssertionErrors.throwz(PKCS7Padding.class);
            }

            public static String encrypt(String key, String data) {
                return encrypt(key, data, AES_CBC_PKCS7_PADDING);
            }

            public static String encrypt(String key, String data, String transformation) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] iv = new byte[PKCS7PADDING_INIT_VECTOR_LENGTH / 2];
                    secureRandom.nextBytes(iv);
                    String initVector = Hexes.toHex(iv);
                    iv = initVector.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipher = Cipher.getInstance(
                        transformation,
                        BouncyCastleProvider.PROVIDER_NAME
                    );
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                    byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                    byte[] encrypted = cipher.doFinal(dataBytes);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(
                        iv.length + encrypted.length);
                    byteBuffer.put(iv);
                    byteBuffer.put(encrypted);

                    return Base64s.encryptBase64(byteBuffer.array());
                }, "AES: handle AES/CBC/PKCS7Padding encrypt failed");
            }

            public static String decrypt(String key, String encrypted) {
                return decrypt(key, encrypted, AES_CBC_PKCS7_PADDING);
            }

            public static String decrypt(String key, String encrypted, String transformation) {
                checkKeyLengthIsValid(key);

                return Cryptos.call(() -> {
                    byte[] encryptedBytes = Base64s.decrypt(encrypted);

                    IvParameterSpec ivSpec = new IvParameterSpec(
                        encryptedBytes,
                        0,
                        PKCS7PADDING_INIT_VECTOR_LENGTH
                    );
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);

                    Cipher cipher = Cipher.getInstance(
                        transformation,
                        BouncyCastleProvider.PROVIDER_NAME
                    );
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] decrypted = cipher.doFinal(
                        encryptedBytes,
                        PKCS7PADDING_INIT_VECTOR_LENGTH,
                        encryptedBytes.length - PKCS7PADDING_INIT_VECTOR_LENGTH
                    );

                    return new String(decrypted, StandardCharsets.UTF_8);
                }, "AES: handle AES/CBC/PKCS7Padding decrypt failed");
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
                return encrypt(key, data, AES_ECB_NO_PADDING);
            }

            public static String encrypt(
                String key,
                String data,
                String transformation) {
                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(transformation);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                    byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

                    return Base64s.encryptBase64(encrypted);
                }, "AES: handle AES/ECB/NoPadding encrypt failed");
            }

            public static String decrypt(String key, String encrypted) {
                return decrypt(key, encrypted, AES_ECB_NO_PADDING);
            }

            public static String decrypt(
                String key,
                String encrypted,
                String transformation) {
                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(transformation);
                    SecretKeySpec keySpec =
                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);

                    byte[] encryptedBytes = Base64s.decrypt(encrypted);
                    byte[] original = cipher.doFinal(encryptedBytes);

                    return new String(original, StandardCharsets.UTF_8).trim();
                }, "AES: handle AES/ECB/NoPadding decrypt failed");
            }
        }

        public static final class PKCS5Padding implements Serializable {

            private PKCS5Padding() {
                AssertionErrors.throwz(CBC.PKCS5Padding.class);
            }

            public static String encrypt(String key, String data) {
                return encrypt(key, data, AES_ECB_PKCS5_PADDING);
            }

            public static String encrypt(String key, String data, String transformation) {

                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(transformation);
                    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM_AES);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);

                    byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

                    return Base64s.encryptBase64(encrypted);
                }, "AES: handle AES/ECB/PKCS5Padding encrypt failed");
            }

            public static String decrypt(String key, String encrypted) {
                return decrypt(key, encrypted, AES_ECB_PKCS5_PADDING);
            }

            public static String decrypt(String key, String encrypted, String transformation) {
                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(transformation);
                    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM_AES);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);

                    byte[] encryptedBytes = Base64s.decrypt(encrypted);
                    byte[] decrypted = cipher.doFinal(encryptedBytes);

                    return new String(decrypted, StandardCharsets.UTF_8).trim();
                }, "AES: handle AES/ECB/PKCS5Padding decrypt failed");
            }
        }

        public static final class PKCS7Padding implements Serializable {

            private PKCS7Padding() {
                AssertionErrors.throwz(PKCS7Padding.class);
            }

            public static String encrypt(String key, String data) {
                return encrypt(key, data, AES_ECB_PKCS7_PADDING);
            }

            public static String encrypt(String key, String data, String transformation) {
                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(
                        transformation,
                        BouncyCastleProvider.PROVIDER_NAME
                    );
                    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM_AES);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);

                    byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

                    return Base64s.encryptBase64(encrypted);
                }, "AES: handle AES/ECB/PKCS7Padding encrypt failed");
            }

            public static String decrypt(String key, String encrypted) {
                return decrypt(key, encrypted, AES_ECB_PKCS7_PADDING);
            }

            public static String decrypt(String key, String encrypted, String transformation) {
                return Cryptos.call(() -> {
                    Cipher cipher = Cipher.getInstance(
                        transformation,
                        BouncyCastleProvider.PROVIDER_NAME
                    );
                    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM_AES);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);

                    byte[] encryptedBytes = Base64s.decrypt(encrypted);
                    byte[] decrypted = cipher.doFinal(encryptedBytes);

                    return new String(decrypted, StandardCharsets.UTF_8).trim();
                }, "AES: handle AES/ECB/PKCS7Padding decrypt failed");
            }
        }
    }

    private static byte[] padIfNecessary(String data, int blockSize) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        int length = dataBytes.length;
        if (length % blockSize != 0) {
            length = length + (blockSize - (length % blockSize));
        }
        byte[] bytes = new byte[length];
        System.arraycopy(dataBytes, 0, bytes, 0, dataBytes.length);

        return bytes;
    }

    private static void checkKeyLengthIsValid(String key) {
        if (determineKeyLengthIsInValid(key)) {
            throw new SecurityException(
                "AES: secret key's length must be 128, 192 or 256 bits");
        }
    }

    public static boolean determineKeyLengthIsInValid(String key) {
        return !determineKeyLengthIsValid(key);
    }

    public static boolean determineKeyLengthIsValid(String key) {
        return key.length() == 16 || key.length() == 24 || key.length() == 32;
    }
}
