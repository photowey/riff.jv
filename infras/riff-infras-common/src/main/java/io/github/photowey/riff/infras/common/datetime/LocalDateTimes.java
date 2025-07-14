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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.github.photowey.riff.infras.common.constant.datetime.DatePatternConstants;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;

/**
 * {@code LocalDateTimes}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public interface LocalDateTimes {

    static String format(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
    }

    static String format(LocalDateTime dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetimes.formatter(pattern);
        return formatter.format(dateTime);
    }

    static String format(LocalDate dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd);
    }

    static String format(LocalDate dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetimes.formatter(pattern);
        return formatter.format(dateTime);
    }

    // ----------------------------------------------------------------

    static String format(LocalTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.HH_mm_ss);
    }

    static String format(LocalTime dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetimes.formatter(pattern);
        return formatter.format(dateTime);
    }

    static String format(Date dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
    }

    static String format(Date dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return format(toLocalDateTime(dateTime), pattern);
    }

    // ----------------------------------------------------------------

    static Date toDate(LocalDate date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // ----------------------------------------------------------------

    static LocalDate toLocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // ----------------------------------------------------------------

    static LocalDateTime toLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    static LocalDateTime toLocalDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    // ----------------------------------------------------------------

    static LocalDateTime toLocalDateTime(String dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return LocalDateTime.parse(dateTime, Datetimes.formatter());
    }

    static LocalDateTime toLocalDateTime(String dateTime, String pattern) {
        if (Strings.isEmpty(dateTime)) {
            return null;
        }

        return LocalDateTime.parse(dateTime, Datetimes.formatter(pattern));
    }

    static LocalDateTime toLocalDateTime(ZonedDateTime from) {
        if (Objects.isNull(from)) {
            return null;
        }

        return fromZoned(from);
    }

    // ----------------------------------------------------------------

    static Long toTimestamp(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.getTime();
    }

    static Long toTimestamp(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    // ----------------------------------------------------------------

    static ZonedDateTime toZoned(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return toZoned(dateTime, ZoneId.systemDefault());
    }

    static ZonedDateTime toZoned(LocalDateTime dateTime, ZoneId zoneId) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return ZonedDateTime.of(dateTime, zoneId);
    }

    // ----------------------------------------------------------------

    static LocalDateTime fromZoned(ZonedDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return dateTime.toLocalDateTime();
    }
}
