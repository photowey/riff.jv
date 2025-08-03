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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.annotation.Nonnull;

import io.github.photowey.riff.infras.common.constant.CommonConstants;
import io.github.photowey.riff.infras.common.util.Lambdas;
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

    public static final String SLASH = "/";
    public static final String QUESTION = "?";
    public static final String AND = "&";
    public static final String EQUALS = "=";
    public static final String UNDERLINE = "_";

    public static final String DEFAULT_SCHEME = "riff";
    public static final String DEFAULT_ACTION = "trigger";

    public static final String SCHEDULE_TYPE_ONCE = "once";
    public static final String SCHEDULE_TYPE_CRON = "cron";
    public static final String SCHEDULE_TYPE_FIXED_RATE = "fixedrate";
    public static final String SCHEDULE_TYPE_FIXED_DELAY = "fixeddelay";
    public static final String DEFAULT_TYPE = SCHEDULE_TYPE_CRON;

    public static final String SCHEDULE_PARAMETER_CRON_EXPRESSION = "expression";
    public static final String SCHEDULE_PARAMETER_INIT_DELAY = "initialDelay";
    public static final String SCHEDULE_PARAMETER_DELAY = "delay";

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

    // ----------------------------------------------------------------

    public static ScheduleContext once() {
        return once(0L, 0L, Lambdas::noOps);
    }

    public static ScheduleContext once(@Nonnull Long initDelay) {
        return once(initDelay, 0L, Lambdas::noOps);
    }

    public static ScheduleContext once(
        @Nonnull Long initDelay,
        @Nonnull Long delay) {
        return once(initDelay, delay, Lambdas::noOps);
    }

    public static ScheduleContext once(
        @Nonnull Long initDelay,
        @Nonnull Long delay,
        @Nonnull Consumer<Map<String, List<String>>> fx) {
        Map<String, List<String>> ctx = new HashMap<>(8);
        ctx.put(SCHEDULE_PARAMETER_INIT_DELAY, List.of(String.valueOf(initDelay)));
        ctx.put(SCHEDULE_PARAMETER_DELAY, List.of(String.valueOf(delay)));

        fx.accept(ctx);

        return ScheduleContext.builder()
            .scheme(DEFAULT_SCHEME)
            .action(DEFAULT_ACTION)
            .type(SCHEDULE_TYPE_ONCE)
            .ctx(ctx)
            .build();
    }

    // ----------------------------------------------------------------

    public static ScheduleContext cron(@Nonnull String expression) {
        return cron(expression, 0L, 0L);
    }

    public static ScheduleContext cron(@Nonnull String expression, @Nonnull Long initDelay) {
        return cron(expression, initDelay, 0L);
    }

    public static ScheduleContext cron(
        @Nonnull String expression, @Nonnull Long initDelay, @Nonnull Long delay) {
        return cron(expression, initDelay, delay, Lambdas::noOps);
    }

    public static ScheduleContext cron(
        @Nonnull String expression,
        @Nonnull Long initDelay,
        @Nonnull Long delay,
        @Nonnull Consumer<Map<String, List<String>>> fx) {
        Map<String, List<String>> ctx = new HashMap<>(8);
        ctx.put(SCHEDULE_PARAMETER_CRON_EXPRESSION, List.of(expression));
        ctx.put(SCHEDULE_PARAMETER_INIT_DELAY, List.of(String.valueOf(initDelay)));
        ctx.put(SCHEDULE_PARAMETER_DELAY, List.of(String.valueOf(delay)));

        fx.accept(ctx);

        return ScheduleContext.builder()
            .scheme(DEFAULT_SCHEME)
            .action(DEFAULT_ACTION)
            .type(DEFAULT_TYPE)
            .ctx(ctx)
            .build();
    }

    // ----------------------------------------------------------------

    public static ScheduleContext fixedRate() {
        return fixedRate(0L, 0L, Lambdas::noOps);
    }

    public static ScheduleContext fixedRate(@Nonnull Long initDelay) {
        return fixedRate(initDelay, 0L, Lambdas::noOps);
    }

    public static ScheduleContext fixedRate(
        @Nonnull Long initDelay,
        @Nonnull Long delay) {
        return fixedRate(initDelay, delay, Lambdas::noOps);
    }

    public static ScheduleContext fixedRate(
        @Nonnull Long initDelay,
        @Nonnull Long delay,
        @Nonnull Consumer<Map<String, List<String>>> fx) {
        Map<String, List<String>> ctx = new HashMap<>(8);
        ctx.put(SCHEDULE_PARAMETER_INIT_DELAY, List.of(String.valueOf(initDelay)));
        ctx.put(SCHEDULE_PARAMETER_DELAY, List.of(String.valueOf(delay)));

        fx.accept(ctx);

        return ScheduleContext.builder()
            .scheme(DEFAULT_SCHEME)
            .action(DEFAULT_ACTION)
            .type(SCHEDULE_TYPE_FIXED_RATE)
            .ctx(ctx)
            .build();
    }

    // ----------------------------------------------------------------

    public static ScheduleContext fixedDelay() {
        return fixedDelay(0L, 0L, Lambdas::noOps);
    }

    public static ScheduleContext fixedDelay(@Nonnull Long initDelay) {
        return fixedDelay(initDelay, 0L, Lambdas::noOps);
    }

    public static ScheduleContext fixedDelay(
        @Nonnull Long initDelay,
        @Nonnull Long delay) {
        return fixedDelay(initDelay, delay, Lambdas::noOps);
    }

    public static ScheduleContext fixedDelay(
        @Nonnull Long initDelay,
        @Nonnull Long delay,
        @Nonnull Consumer<Map<String, List<String>>> fx) {
        Map<String, List<String>> ctx = new HashMap<>(8);
        ctx.put(SCHEDULE_PARAMETER_INIT_DELAY, List.of(String.valueOf(initDelay)));
        ctx.put(SCHEDULE_PARAMETER_DELAY, List.of(String.valueOf(delay)));

        fx.accept(ctx);

        return ScheduleContext.builder()
            .scheme(DEFAULT_SCHEME)
            .action(DEFAULT_ACTION)
            .type(SCHEDULE_TYPE_FIXED_DELAY)
            .ctx(ctx)
            .build();
    }

    // ----------------------------------------------------------------

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

            return tryParse(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    public String parameter(String key) {
        return this.parameter(key, Function.identity());
    }

    public <T> T parameter(String key, Function<String, T> mapper) {
        return this.ctx.getOrDefault(key, List.of())
            .stream()
            .findFirst()
            .map(mapper)
            .orElse(null);
    }

    // ----------------------------------------------------------------

    private static ScheduleContext tryParse(URI uri) {
        String queryString = uri.getQuery();
        Map<String, List<String>> ctx = parseQueryParameters(queryString);

        return ScheduleContext.builder()
            .scheme(uri.getScheme())
            .action(uri.getHost())
            .type(uri.getPath().replaceAll(SLASH, CommonConstants.Symbol.EMPTY))
            .query(URLDecoder.decode(queryString, StandardCharsets.UTF_8))
            .ctx(ctx)
            .build();
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
