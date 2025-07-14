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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.photowey.riff.infras.common.constant.CommonConstants;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Collections}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public final class Collections {

    private Collections() {
        AssertionErrors.throwz(Collections.class);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isEmpty(Collection<T> haystack) {
        return null == haystack || haystack.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> haystack) {
        return !isEmpty(haystack);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isNotContains(Collection<T> haystack, T needle) {
        return !isContains(haystack, needle);
    }

    public static <T> boolean isContains(Collection<T> haystack, T needle) {
        if (isEmpty(haystack) || Objects.isNull(needle)) {
            return false;
        }

        return haystack.contains(needle);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isSingle(Collection<T> haystack) {
        if (isEmpty(haystack)) {
            return false;
        }

        return CommonConstants.Number.ONE == haystack.size();
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> List<T> newArrayList(T... elements) {
        return asMutableList(elements);
    }

    @SafeVarargs
    public static <T> Set<T> newHashSet(T... elements) {
        return asMutableSet(elements);
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> List<T> asMutableList(T... elements) {
        return Arrays.asMutableList(elements);
    }

    @SafeVarargs
    public static <T> List<T> asImmutableList(T... elements) {
        return Arrays.asImmutableList(elements);
    }

    @SafeVarargs
    public static <T> Set<T> asMutableSet(T... elements) {
        return Arrays.asMutableSet(elements);
    }

    @SafeVarargs
    public static <T> Set<T> asImmutableSet(T... elements) {
        return Arrays.asImmutableSet(elements);
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> List<T> copyList(T... elements) {
        return Arrays.asMutableList(elements);
    }

    public static <T> List<T> copyList(List<T> elements) {
        return new ArrayList<>(elements);
    }

    public static <T> List<T> copyList(Set<T> elements) {
        return new ArrayList<>(elements);
    }

    @SafeVarargs
    public static <T> Set<T> copySet(T... elements) {
        return Arrays.asMutableSet(elements);
    }

    public static <T> Set<T> copySet(List<T> elements) {
        return new HashSet<>(elements);
    }

    public static <T> Set<T> copySet(Set<T> elements) {
        return new HashSet<>(elements);
    }

    // ----------------------------------------------------------------

    @SafeVarargs
    public static <T> List<T> immutableCopyList(T... elements) {
        return Arrays.asImmutableList(elements);
    }

    public static <T> List<T> immutableCopyList(List<T> elements) {
        return java.util.Collections.unmodifiableList(elements);
    }

    public static <T> List<T> immutableCopyList(Set<T> elements) {
        return List.copyOf(elements);
    }

    @SafeVarargs
    public static <T> Set<T> immutableCopySet(T... elements) {
        return Arrays.asImmutableSet(elements);
    }

    public static <T> Set<T> immutableCopySet(List<T> elements) {
        return Set.copyOf(elements);
    }

    public static <T> Set<T> immutableCopySet(Set<T> elements) {
        return java.util.Collections.unmodifiableSet(elements);
    }

    // ----------------------------------------------------------------

    public static <T> List<T> emptyList() {
        return emptyList(0);
    }

    public static <T> List<T> emptyList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    public static <T> Set<T> emptySet() {
        return emptySet(0);
    }

    public static <T> Set<T> emptySet(int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }
}
