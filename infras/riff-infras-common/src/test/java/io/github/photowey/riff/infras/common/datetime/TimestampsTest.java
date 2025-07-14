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
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code TimestampsTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
class TimestampsTest {

    @Test
    void testTrimTail_Timestamp() {
        long now = 1752491157888L;

        Long trimmed = Timestamps.trimTail(now);
        Assertions.assertNotNull(trimmed);
        Assertions.assertEquals(0, trimmed % 1000);
    }

    @Test
    void testTrimTail_LocalDateTime() {
        long now = 1752491157888L;
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.systemDefault());

        // 1752491157888L
        long epochMilli = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assertions.assertTrue(epochMilli % 1000 > 0);

        // 1752491157000L
        Long trimmed = Timestamps.toTimestamp(dateTime);
        Assertions.assertNotNull(trimmed);
        Assertions.assertEquals(1752491157000L, trimmed);
        Assertions.assertEquals(0, trimmed % 1000);
    }

}
