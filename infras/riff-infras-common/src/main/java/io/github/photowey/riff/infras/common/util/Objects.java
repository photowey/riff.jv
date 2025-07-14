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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Supplier;

import io.github.photowey.riff.infras.common.thrower.AssertionErrors;

/**
 * {@code Objects}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public final class Objects {

    private Objects() {
        AssertionErrors.throwz(Objects.class);
    }

    // ----------------------------------------------------------------

    public static <T> T defaultIfNull(T target, T defaultValue) {
        return target != null ? target : defaultValue;
    }

    public static <T> T tryDefaultIfNull(T target, Supplier<T> fx) {
        if (target != null) {
            return target;
        }

        return fx.get();
    }

    // ----------------------------------------------------------------

    public static <T extends Collection<E>, E> T defaultIfEmpty(T target, T defaultValue) {
        return Collections.isEmpty(target) ? defaultValue : target;
    }

    public static <T extends Collection<E>, E> T tryDefaultIfEmpty(T target, Supplier<T> fx) {
        if (Collections.isEmpty(target)) {
            return fx.get();
        }

        return target;
    }

    // ----------------------------------------------------------------

    public static <T> boolean isNull(T target) {
        return java.util.Objects.isNull(target);
    }

    public static <T> boolean nonNull(T target) {
        return java.util.Objects.nonNull(target);
    }

    public static <T> boolean isNotNull(T target) {
        return nonNull(target);
    }

    public static <T> boolean isEmpty(T target) {
        return isNull(target);
    }

    public static <T> boolean isNotEmpty(T target) {
        return nonNull(target);
    }

    public static <T> T requireNonNull(T target) {
        return java.util.Objects.requireNonNull(target);
    }

    // ----------------------------------------------------------------

    public static <T extends Number> boolean predicateNumberIsNotEquals(T one, T other) {
        return !predicateNumberIsEquals(one, other);
    }

    public static <T extends Number> boolean predicateNumberIsEquals(T one, T other) {
        return isNotNull(one)
            && isNotNull(other)
            && new BigDecimal(one.toString()).compareTo(new BigDecimal(other.toString())) == 0;
    }

    // ----------------------------------------------------------------

    public static <T> boolean notEquals(T one, T other) {
        return !equals(one, other);
    }

    public static <T> boolean equals(T one, T other) {
        return java.util.Objects.equals(one, other);
    }

    public static <T> boolean deepEquals(T one, T other) {
        return java.util.Objects.deepEquals(one, other);
    }
}
