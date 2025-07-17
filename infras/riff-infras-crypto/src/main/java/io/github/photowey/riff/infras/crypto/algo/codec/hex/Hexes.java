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

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Hexes}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public final class Hexes {

    private static final int HEX_BYTES_LENGTH = 2;

    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private Hexes() {
        AssertionErrors.throwz(Hexes.class);
    }

    public static String toHex(String input) {
        return byteToHex(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHex(byte[] input) {
        return byteToHex(input);
    }

    // ----------------------------------------------------------------

    public static byte[] toBytes(String hex) {
        return hexToBytes(hex);
    }

    public static String toOriginal(String hex) {
        byte[] bytes = hexToBytes(hex);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    // ----------------------------------------------------------------

    private static byte[] hexToBytes(String hex) {
        return hexToBytes(hex, true);
    }

    private static byte[] hexToBytes(String hex, boolean nullable) {
        if (hex == null) {
            if (!nullable) {
                throw new IllegalArgumentException("Hex string cannot be null");
            }

            return new byte[0];
        }

        int len = hex.length();
        if (len % HEX_BYTES_LENGTH != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }

        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += HEX_BYTES_LENGTH) {
            int highNibble = Character.digit(hex.charAt(i), 16);
            int lowNibble = Character.digit(hex.charAt(i + 1), 16);

            if (highNibble == -1 || lowNibble == -1) {
                throw new IllegalArgumentException(
                    "Hex string contains non-hexadecimal character at position " + i);
            }

            bytes[i >>> 1] = (byte) ((highNibble << 4) | lowNibble);
        }

        return bytes;
    }

    private static String byteToHex(byte[] input) {
        StringBuilder buf = new StringBuilder();
        for (byte b : input) {
            buf.append(HEX_CHARS[(b >>> 4) & 0x0f]);
            buf.append(HEX_CHARS[b & 0x0f]);
        }

        return buf.toString();
    }
}
