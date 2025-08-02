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
package io.github.photowey.riff.business.job.core.context;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.photowey.riff.infras.common.util.Maps;
import io.github.photowey.riff.infras.common.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ScheduleContext}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleContext implements Serializable {

    @Serial
    private static final long serialVersionUID = -5315563844558346463L;

    private static final String SLASH = "/";
    private static final String QUESTION = "?";
    private static final String AND = "&";
    private static final String EQUALS = "=";
    private static final String UNDERLINE = "_";

    private static final String DEFAULT_SCHEME = "riff";
    private static final String DEFAULT_ACTION = "trigger";

    /**
     * {@code riff://trigger/once?initialDelay=0&delay=0}
     * {@code riff://trigger/cron?expression=0%2F5+*+*+*+*+%3F&initialDelay=0&delay=0}
     * {@code riff://trigger/fixedrate?initialDelay=0&delay=0}
     * {@code riff://trigger/fixeddelay?initialDelay=0&delay=0}
     */

    private String scheme;
    private String action;
    private String type;
    private String query;

    private Map<String, List<String>> ctx;

    public String compact() {
        StringBuilder buf = new StringBuilder();

        if (Strings.isEmpty(this.scheme)) {
            this.setScheme(DEFAULT_SCHEME);
        }
        buf.append(this.scheme).append("://");

        if (Strings.isEmpty(this.action)) {
            this.setAction(DEFAULT_ACTION);
        }
        buf.append(this.action);

        if (Strings.isEmpty(this.type)) {
            throw new IllegalArgumentException("type can't be null");
        }
        buf.append(SLASH).append(this.type);

        if (Maps.isNotEmpty(this.ctx)) {
            buf.append(QUESTION);
            List<String> params = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : this.ctx.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
                    params.add(key + EQUALS + encodedValue);
                }
            }

            buf.append(String.join(AND, params));
        }

        return buf.toString();
    }

    public static ScheduleContext parse(String compacted) {
        try {
            URI uri = new URI(compacted);

            String queryString = uri.getQuery();
            Map<String, List<String>> ctx = parseQueryParameters(queryString);

            return ScheduleContext.builder()
                .scheme(uri.getScheme())
                .action(uri.getHost())
                .type(uri.getPath().replaceAll(SLASH, ""))
                .query(URLDecoder.decode(queryString, StandardCharsets.UTF_8))
                .ctx(ctx)
                .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, List<String>> parseQueryParameters(String query) {
        Map<String, List<String>> ctx = new LinkedHashMap<>();
        if (Strings.isEmpty(query)) {
            return ctx;
        }

        for (String pair : query.split(AND)) {
            String[] entry = pair.split(EQUALS, 2);
            String key = entry[0];
            String value = entry.length > 1 ? entry[1] : "";

            ctx.computeIfAbsent(
                key, k -> new ArrayList<>()).add(URLDecoder.decode(value, StandardCharsets.UTF_8)
            );
        }

        return ctx;
    }

    // ----------------------------------------------------------------

    public String scheme() {
        return scheme;
    }

    public String action() {
        return action;
    }

    public String type() {
        return type;
    }

    public String query() {
        return query;
    }

    public Map<String, List<String>> ctx() {
        return ctx;
    }
}
