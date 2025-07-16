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
package io.github.photowey.riff.infras.authentication.core.constant;

/**
 * {@code AuthorityConstants}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public interface AuthorityConstants {

    // ----------------------------------------------------------

    // DEFAULT CONSTANTS

    String DEFAULT_TENANT = "saas";
    String DEFAULT_PLATFORM = "saas";
    String DEFAULT_APP = "boss";
    String DEFAULT_CLIENT = "web";
    String DEFAULT_ANONYMOUS_USERNAME = "anonymous";

    // ----------------------------------------------------------

    String PROTOCOL_TEMPLATE = "{}{}";
    String ACCOUNT_PROTOCOL = "account://";
    String OAUTH_CLIENT_PROTOCOL = "oauthclient://";

    // ----------------------------------------------------------

    String PASSPORT_AUTHENTICATION_CLIENT_WEB = "web";
    String PASSPORT_AUTHENTICATION_CLIENT_OAUTHCLIENT = "oauthclient";

    // ----------------------------------------------------------

    String SPRING_SECURITY_AUTHORITY_PREFIX = "ROLE_";
    String SPRING_OAUTH2_SCOPE_PREFIX = "SCOPE_";

    String AUTHORITY_SCOPE_KEY = "scopes";
    String AUTHORITY_ROLE_KEY = "roles";

    String WEB_AUTH_SCOPE = "web";
    String OAUTHCLIENT_AUTH_SCOPE = "oauth";
    String THIRDPARTY_AUTH_SCOPE = "thirdparty";


    String PASSWORD_MODE_AUTH_SCOPE = "pwd";

    // ----------------------------------------------------------

    String AUTHORIZATION_HEADER = "Authorization";

    String AUTHORIZATION_TOKEN_PREFIX_BEARER = "Bearer";
    String AUTHORIZATION_TOKEN_PREFIX_OAUTH = "OAuth";

    // ----------------------------------------------------------

    String AUTHORITY_ISSUER_KEY = "iss";
    String AUTHORITY_ISSUE_AT_KEY = "iat";
    String AUTHORITY_JWT_ID_KEY = "jti";
    String AUTHORITY_AUDIT_KEY = "aud";
    String AUTHORITY_CLIENT_KEY = "xci";
    String AUTHORITY_AUTH_KEY = "ath";

    String AUTHORITY_ALL = "*.*";

    // ----------------------------------------------------------

    /**
     * admin.d@riff.jv
     */
    String DEFAULT_PASSWORD = "$2a$10$z5x49Czg.IrV6sUy6p01mel9YoBeBGpMFQ5QsYwQYFD59M3iZs63m";

    // ---------------------------------------------------------- Token

    String TOKEN_ALGO_HS512 = "HmacSHA512";

    // ----------------------------------------------------------

    long DUMMY_LOGIN_USER_ID = 0L;

    // ----------------------------------------------------------

    String[] DEFAULT_IGNORED_PATHS = new String[] {
        "/favicon.ico",
        "/webjars/**",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/doc.html",
        "/swagger-ui.html",
        "/actuator/**",
        "/healthy",
        "/error"
    };
}
