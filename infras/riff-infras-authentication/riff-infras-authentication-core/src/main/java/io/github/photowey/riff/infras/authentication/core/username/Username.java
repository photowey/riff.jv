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
package io.github.photowey.riff.infras.authentication.core.username;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.common.formatter.StringFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Username}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Username implements Serializable {

    @Serial
    private static final long serialVersionUID = 5477934765906380818L;

    private static final String PASSPORT_TEMPLATE = "{}?tenant={}&platform={}&app={}&client={}&type={}&rememberMe={}";
    private static final Pattern PT =
        Pattern.compile("(.*)\\?tenant=(.*)&platform=(.*)&app=(.*)&client=(.*)&type=(.*)&rememberMe=(.*)");

    private String tenant;
    private String platform;
    private String app;

    private String client;

    /**
     * 1: web | username
     * 2: oauthclient | accessKey
     * 3: cmder | accessKey
     */
    private Integer type;

    /**
     * Username | AccessKey
     */
    private String username;
    private Integer rememberMe;

    public String compact() {
        return StringFormatter.format(
            PASSPORT_TEMPLATE,
            this.username,
            this.tenant, this.platform, this.app, this.client,
            this.type,
            this.rememberMe
        );
    }

    public static Username parse(String proxy) {
        Matcher matcher = PT.matcher(proxy);
        if (matcher.matches()) {
            return Username.builder()
                .username(matcher.group(1))
                .tenant(matcher.group(2))
                .platform(matcher.group(3))
                .app(matcher.group(4))
                .client(matcher.group(5))
                .type(Integer.parseInt(matcher.group(6)))
                .rememberMe(Integer.parseInt(matcher.group(7)))
                .build();
        }

        throw new SecurityAuthenticationException("Invalid username proxy pattern:[" + proxy + "]");
    }

    // ----------------------------------------------------------------

    public String tenant() {
        return tenant;
    }

    public String platform() {
        return platform;
    }

    public String app() {
        return app;
    }

    public String client() {
        return client;
    }

    public Integer type() {
        return type;
    }

    public String username() {
        return username;
    }

    public Integer rememberMe() {
        return rememberMe;
    }
}
