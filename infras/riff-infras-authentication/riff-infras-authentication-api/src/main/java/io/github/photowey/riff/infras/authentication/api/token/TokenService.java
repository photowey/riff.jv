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
package io.github.photowey.riff.infras.authentication.api.token;

import org.springframework.security.core.Authentication;

/**
 * {@code TokenService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public interface TokenService
    extends TokenValidatorService, AuthenticationService, TokenParserService {

    /**
     * Create {@code Token}.
     *
     * @param authentication the {@link Authentication}
     * @return the {@code Token}.
     */
    default String createToken(Authentication authentication) {
        return this.createToken(authentication, false);
    }

    /**
     * Create {@code Token}.
     *
     * @param authentication the {@link Authentication}
     * @param rememberMe     whether to remember me
     * @return the {@code Token}.
     */
    String createToken(Authentication authentication, boolean rememberMe);
}

