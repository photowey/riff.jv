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
package io.github.photowey.riff.core.constant;

/**
 * {@code CommonConstants}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public interface CommonConstants {

    interface Symbol {
        String SLASH = "/";

        String DOT = ".";
        String COMMA = ",";
        String SEMICOLON = ";";
        String UNDERLINE = "_";
        String DASH = "-";
    }

    interface Byte {
        long BYTE = 1L;
        long KB = BYTE * 1024L;
        long MB = KB * 1024L;
        long GB = MB * 1024L;
        long TB = GB * 1024L;
        long PB = TB * 1024L;

        static long toByte(long x) {
            return x * BYTE;
        }

        static long toKb(long x) {
            return x * KB;
        }

        static long toMb(long x) {
            return x * MB;
        }

        static long toGb(long x) {
            return x * GB;
        }

        static long toTb(long x) {
            return x * TB;
        }

        static long toPb(long x) {
            return x * PB;
        }
    }

    interface Number {
        int ZERO = 0;
        int ONE = 1;

        int TEN = 10;
        int HUNDRED = 100;
        int THOUSAND = 1000;
        int TEN_THOUSAND = 10000;

        int HUNDRED_THOUSAND = 10 * TEN_THOUSAND;
        int MILLION = 10 * HUNDRED_THOUSAND;
        int TEN_MILLION = 10 * MILLION;
        int HUNDRED_MILLION = 10 * TEN_MILLION;
        int BILLION = 10 * HUNDRED_MILLION;
        long TEN_BILLION = BILLION * 10L;

        long ZERO_LONG = ZERO;
        long ONE_LONG = ONE;
        long TEN_LONG = TEN;
        long HUNDRED_LONG = HUNDRED;
        long THOUSAND_LONG = THOUSAND;
    }
}
