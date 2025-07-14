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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Arrays}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public final class Arrays {

    private Arrays() {
        AssertionErrors.throwz(Arrays.class);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isEmpty(T[] elements) {
        return null == elements || elements.length == 0;
    }

    public static boolean isEmpty(byte[] elements) {
        return null == elements || elements.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] elements) {
        return !isEmpty(elements);
    }

    public static boolean isNotEmpty(byte[] elements) {
        return !isEmpty(elements);
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> List<T> asMutableList(T... elements) {
        List<T> haystack = new ArrayList<>();
        if (Arrays.isNotEmpty(elements)) {
            haystack.addAll(java.util.Arrays.asList(elements));
        }

        return haystack;
    }

    @SafeVarargs
    public static <T> List<T> asImmutableList(T... elements) {
        return asList(elements);
    }

    @SafeVarargs
    public static <T> List<T> asList(T... elements) {
        return java.util.Arrays.asList(elements);
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> Set<T> asMutableSet(T... elements) {
        Set<T> haystack = new HashSet<>();
        if (Arrays.isNotEmpty(elements)) {
            haystack.addAll(java.util.Arrays.asList(elements));
        }

        return haystack;
    }

    @SafeVarargs
    public static <T> Set<T> asImmutableSet(T... elements) {
        return asSet(elements);
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        return Stream.of(elements).collect(Collectors.toUnmodifiableSet());
    }
}
