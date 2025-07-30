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
package io.github.photowey.riff.business.job.core.checker.exception;

import java.util.Collection;

import io.github.photowey.riff.business.job.core.exception.JobException;
import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

/**
 * {@code AbstractJobExceptionChecker}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/30
 */
public abstract class AbstractJobExceptionChecker {

    public static <T> T checkNotNull(T t, String message, Object... args) {
        if (Objects.nonNull(t)) {
            return t;
        }

        return throwUnchecked(message, args);
    }

    public static <T> T checkNull(T t, String message, Object... args) {
        if (Objects.isNull(t)) {
            return null;
        }

        return throwUnchecked(message, args);
    }

    // ----------------------------------------------------------------

    public static String checkNotBlank(String t, String message, Object... args) {
        if (Strings.isNotEmpty(t)) {
            return t;
        }

        return throwUnchecked(message, args);
    }

    public static String checkBlank(String t, String message, Object... args) {
        if (Strings.isEmpty(t)) {
            return t;
        }

        return throwUnchecked(message, args);
    }

    // ----------------------------------------------------------------

    public static <T> Collection<T> checkNotEmpty(Collection<T> t, String message, Object... args) {
        if (Collections.isNotEmpty(t)) {
            return t;
        }

        return throwUnchecked(message, args);
    }

    public static <T> Collection<T> checkEmpty(Collection<T> t, String message, Object... args) {
        if (Collections.isEmpty(t)) {
            return t;
        }

        return throwUnchecked(message, args);
    }

    // ----------------------------------------------------------------

    public static void checkTrue(boolean expression, String message, Object... args) {
        if (expression) {
            return;
        }

        throwUnchecked(message, args);
    }

    public static void checkFalse(boolean expression, String message, Object... args) {
        checkTrue(!expression, message, args);
    }

    // ----------------------------------------------------------------

    public static <T> T throwUnchecked(ExceptionStatus status) {
        throw new JobException(status);
    }

    public static <T> T throwUnchecked(ExceptionStatus status, String message, Object... args) {
        throw new JobException(status, formatMessage(message, args));
    }

    public static <T> T throwUnchecked(String message, Object... args) {
        throw new JobException(formatMessage(message, args));
    }

    // ----------------------------------------------------------------

    private static String formatMessage(String message, Object... args) {
        return StringFormatter.format(message, args);
    }
}
