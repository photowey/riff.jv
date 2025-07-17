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
package io.github.photowey.riff.infras.common.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.annotation.Nonnull;

import io.github.photowey.riff.infras.common.constant.datetime.DatePatternConstants;

/**
 * {@code Datetimes}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public interface Datetimes {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DatePatternConstants.yyyy_MM_dd);
    DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DatePatternConstants.yyMMdd);

    // ----------------------------------------------------------------

    static DateTimeFormatter formatter(@Nonnull String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    static DateTimeFormatter formatter() {
        return DATE_TIME_FORMATTER;
    }

    static DateTimeFormatter dateFormatter() {
        return DATE_FORMATTER;
    }

    static DateTimeFormatter shortDateFormatter() {
        return SHORT_DATE_FORMATTER;
    }

    // ----------------------------------------------------------------

    static LocalDateTime now() {
        return LocalDateTime.now();
    }

    static String now(@Nonnull String pattern) {
        LocalDateTime now = LocalDateTime.now();

        return formatter(pattern).format(now);
    }

    // ----------------------------------------------------------------

    static String today() {
        return today(DatePatternConstants.yyMMdd);
    }

    static String today(@Nonnull String pattern) {
        LocalDateTime now = LocalDateTime.now();

        return LocalDateTimes.format(now, pattern);
    }

    // ----------------------------------------------------------------

    static boolean betweenNow(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end) {
        LocalDateTime now = now();
        return start.isBefore(now) && end.isAfter(now);
    }
}
