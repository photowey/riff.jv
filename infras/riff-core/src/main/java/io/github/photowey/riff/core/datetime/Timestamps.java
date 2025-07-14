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
package io.github.photowey.riff.core.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import io.github.photowey.riff.core.constant.datetime.DatetimeConstants;
import io.github.photowey.riff.core.util.Objects;

/**
 * {@code Timestamps}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
public interface Timestamps {

    /**
     * Trims the tail millis if necessary.
     *
     * @param dateTime the {@link LocalDateTime} to trim
     * @return the trimmed {@link LocalDateTime}
     */
    static LocalDateTime trimTail(LocalDateTime dateTime) {
        Long ts = toTimestamp(dateTime);

        return toLocalDateTime(ts);
    }

    /**
     * Trims the tail millis if necessary.
     *
     * @param timestamp the {@code timestamp} to trim
     * @return the trimmed {@code timestamp}
     */
    static Long trimTail(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }

        return timestamp / DatetimeConstants.MILLIS_OF_SECONDS * DatetimeConstants.MILLIS_OF_SECONDS;
    }

    /**
     * Converts the {@link LocalDateTime} to {@code timestamp}.
     *
     * @param dateTime the {@link LocalDateTime} to convert
     * @return the converted {@code timestamp}
     */
    static Long toTimestamp(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }

        return trimTail(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * Converts the {@code timestamp} to {@link LocalDateTime}.
     *
     * <p>Trims tail millis if necessary.</p>
     *
     * @param timestamp the {@code timestamp} to convert
     * @return the converted {@link LocalDateTime}
     */
    static LocalDateTime toLocalDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(trimTail(timestamp)), ZoneId.systemDefault());
    }
}
