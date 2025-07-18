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
package io.github.photowey.riff.infras.authentication.property;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;

import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code SecurityProperties}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 4844136551777056675L;

    private static final String DEFAULT_PREFIX = "spring.security.riff.global.security";
    private static final String RIFF_SECURITY_PROPERTY_ENV_KEY = "RIFF_SECURITY_PROPERTY_PREFIX";
    private static final String RIFF_SECURITY_PROPERTY_PROPERTY_KEY = "io.github.photowey.riff.global.security.prefix";

    @Valid
    private CorsConfiguration cors = new CorsConfiguration();
    @Valid
    private Auth auth = new Auth();
    @Valid
    private IgnorePath ignores = new IgnorePath();

    @Data
    @Validated
    public static class Auth implements Serializable {

        @Serial
        private static final long serialVersionUID = -5177116394349152348L;

        @Valid
        private Issuer issuer = new Issuer();
        @Valid
        private Jwt jwt = new Jwt();

        @Valid
        private Loader loader = new Loader();
        @Valid
        private Checker checker = new Checker();

        public Issuer issuer() {
            return issuer;
        }

        public Jwt jwt() {
            return jwt;
        }

        public Loader loader() {
            return loader;
        }

        public Checker checker() {
            return checker;
        }
    }


    @Data
    @Validated
    public static class Loader implements Serializable {

        @Serial
        private static final long serialVersionUID = 5802519965188417802L;

        private String name = "local";

        public String name() {
            return name;
        }
    }

    @Data
    @Validated
    public static class Checker implements Serializable {

        @Serial
        private static final long serialVersionUID = 5016582036943907532L;

        private String name = "local";

        public String name() {
            return name;
        }
    }

    @Data
    @Validated
    public static class Issuer implements Serializable {

        @Serial
        private static final long serialVersionUID = -2512203819892599541L;

        @NotBlank(message = "The Issuer secret can't be blank")
        @Size(min = 32, message = "The Issuer secret must be at least 32 characters")
        private String secret;
        @NotBlank(message = "The Issuer URI can't be blank")
        private String uri;

        public String secret() {
            return secret;
        }

        public String uri() {
            return uri;
        }
    }

    @Data
    @Validated
    public static class Jwt implements Serializable {

        @Serial
        private static final long serialVersionUID = 7166000514675396420L;

        @NotBlank(message = "The jwt secret can't be blank")
        @Size(min = 64, message = "The jwt secret must be at least 64 characters")
        private String secret;
        private String authorities = "ath";

        private long tokenValidityInSeconds = TimeUnit.DAYS.toSeconds(1);
        private long refreshTokenValidityInSeconds = TimeUnit.DAYS.toSeconds(30);
        private long tokenValidityInSecondsForRememberMe = TimeUnit.DAYS.toSeconds(7);

        public String secret() {
            return secret;
        }

        public String authorities() {
            return authorities;
        }

        public long tokenValidityInSeconds() {
            return tokenValidityInSeconds;
        }

        public long refreshTokenValidityInSeconds() {
            return refreshTokenValidityInSeconds;
        }

        public long tokenValidityInSecondsForRememberMe() {
            return tokenValidityInSecondsForRememberMe;
        }
    }

    @Data
    @Validated
    public static class IgnorePath implements Serializable {

        @Serial
        private static final long serialVersionUID = -2486718833719722665L;

        private List<String> paths;

        public List<String> paths() {
            if (Objects.isNull(this.paths)) {
                this.paths = Collections.emptyList();
            }

            return paths;
        }
    }

    // ----------------------------------------------------------------

    public CorsConfiguration cors() {
        return cors;
    }

    public Auth auth() {
        return auth;
    }

    public IgnorePath ignores() {
        return ignores;
    }

    // ----------------------------------------------------------------

    public static String determineSecurityPropertyPrefix() {
        String envPrefix = System.getenv(RIFF_SECURITY_PROPERTY_ENV_KEY);
        if (Strings.isNotEmpty(envPrefix)) {
            return envPrefix;
        }

        String propPrefix = System.getProperty(RIFF_SECURITY_PROPERTY_PROPERTY_KEY);
        if (Strings.isNotEmpty(propPrefix)) {
            return propPrefix;
        }

        return getPrefix();
    }

    public static String getPrefix() {
        return DEFAULT_PREFIX;
    }
}
