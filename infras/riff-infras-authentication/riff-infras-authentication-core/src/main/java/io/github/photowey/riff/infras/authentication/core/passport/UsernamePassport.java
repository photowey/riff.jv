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
package io.github.photowey.riff.infras.authentication.core.passport;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.common.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code UsernamePassport}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePassport implements Serializable {

    private static final String DUMMY_VALUE = "-";
    private static final Pattern PT =
        Pattern.compile("(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*)");

    private String tenant;
    private String platform;
    private String app;
    private String client;

    private Long userId;

    private String username;
    private String mobile;

    private Integer type;
    private String subject;

    public String compact() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static UsernamePassport parse(String proxy) {
        Matcher matcher = PT.matcher(proxy);
        if (matcher.matches()) {
            return UsernamePassport.builder()
                .build();
        }

        throw new SecurityAuthenticationException("Unreachable here.");
    }

    // ----------------------------------------------------------------

    private boolean determineIsDummyMobile() {
        return Strings.isNotEmpty(this.mobile)
            && this.mobile.equalsIgnoreCase(DUMMY_VALUE);
    }

    // ----------------------------------------------------------------

    public String getMobile() {
        if (Strings.isNotEmpty(this.mobile)
            && this.determineIsDummyMobile()) {
            return null;
        }

        return mobile;
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

    public Long userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String mobile() {
        return this.getMobile();
    }

    public Integer type() {
        return type;
    }

    public String subject() {
        return subject;
    }

    public static String dummyValue() {
        return DUMMY_VALUE;
    }

    public static boolean determineIsDummyValue(String target) {
        return target.equalsIgnoreCase(DUMMY_VALUE);
    }
}
