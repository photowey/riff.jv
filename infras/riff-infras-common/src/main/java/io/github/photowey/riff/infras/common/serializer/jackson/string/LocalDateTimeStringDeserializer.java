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
package io.github.photowey.riff.infras.common.serializer.jackson.string;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.photowey.riff.infras.common.constant.datetime.DatePatternConstants;
import io.github.photowey.riff.infras.common.datetime.Datetimes;
import io.github.photowey.riff.infras.common.util.Strings;

/**
 * {@code LocalDateTimeStringDeserializer}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public class LocalDateTimeStringDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final int SHORT_FORMATTER_TEMPLATE_LENGTH = DatePatternConstants.yyyy_MM_dd.length();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
        throws IOException {
        String datetime = p.getValueAsString();
        if (Strings.isNotEmpty(datetime)) {
            // yyyy-MM-dd HH:mm:ss
            if (datetime.length() > SHORT_FORMATTER_TEMPLATE_LENGTH) {
                return LocalDateTime.parse(datetime, Datetimes.formatter());
            }

            // yyyy-MM-dd
            return LocalDateTime.parse(datetime, Datetimes.shortDateFormatter());
        }

        return null;
    }
}
