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
package io.github.photowey.riff.infras.common.util;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Strings}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public final class Strings {

    private Strings() {
        AssertionErrors.throwz(Strings.class);
    }

    // ----------------------------------------------------------------

    public static String defaultIfEmpty(String target, String defaultValue) {
        return isNotEmpty(target) ? target : defaultValue;
    }

    // ----------------------------------------------------------------

    public static boolean isEmpty(CharSequence cs) {
        if (cs == null) {
            return true;
        }

        if (cs instanceof String str) {
            return str.trim().isEmpty();
        }

        return cs.isEmpty();
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    // ----------------------------------------------------------------

    public static boolean isNotContains(String haystack, String needle) {
        return !isContains(haystack, needle);
    }

    public static boolean isContains(String haystack, String needle) {
        if (isEmpty(haystack) || isEmpty(needle)) {
            return false;
        }

        return haystack.contains(needle);
    }

    // ----------------------------------------------------------------

    public static boolean isNotNumeric(CharSequence cs) {
        return !isNumeric(cs);
    }

    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }

        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // ----------------------------------------------------------------

    public static boolean isNotEquals(String one, String other) {
        return notEquals(one, other);
    }

    public static boolean isEquals(String one, String other) {
        return equals(one, other);
    }

    public static boolean notEquals(String one, String other) {
        return !equals(one, other);
    }

    public static boolean equals(String one, String other) {
        return isNotEmpty(one) && one.equals(other);
    }
}
