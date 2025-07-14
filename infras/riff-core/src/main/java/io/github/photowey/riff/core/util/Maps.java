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
package io.github.photowey.riff.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.photowey.riff.core.thrower.AssertionErrors;

/**
 * {@code Maps}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public final class Maps {

    public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);
    private static final int THRESHOLD_SIZE = 3;

    private Maps() {
        AssertionErrors.throwz(Maps.class);
    }

    // ----------------------------------------------------------------

    public static <K, V> Map<K, V> emptyMap() {
        return new HashMap<>(0);
    }

    public static <K, V> Map<K, V> emptyMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    public static <K, V> ConcurrentHashMap<K, V> emptyConcurrentMap() {
        return new ConcurrentHashMap<>(0);
    }

    /**
     * ref: com.google.common.collect.Maps#newHashMapWithExpectedSize
     *
     * @param expectedSize size
     * @param <K>          KEY
     * @param <V>          VALUE
     * @return Map&lt;K,V&gt;
     */
    public static <K, V> Map<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(capacity(expectedSize));
    }

    static int capacity(int expectedSize) {
        if (expectedSize < THRESHOLD_SIZE) {
            checkNonnegative(expectedSize, "expectedSize");
            return expectedSize + 1;
        }

        if (expectedSize < MAX_POWER_OF_TWO) {
            return (int) Math.ceil(expectedSize / 0.75);
        }

        return Integer.MAX_VALUE;
    }

    // ----------------------------------------------------------------

    public static <K, V> boolean isEmpty(Map<K, V> collection) {
        return null == collection || collection.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> collection) {
        return !isEmpty(collection);
    }

    // ----------------------------------------------------------------

    public static int checkNonnegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
        }

        return value;
    }
}
