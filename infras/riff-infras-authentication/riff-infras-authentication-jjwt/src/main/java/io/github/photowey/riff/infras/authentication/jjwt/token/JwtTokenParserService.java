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
package io.github.photowey.riff.infras.authentication.jjwt.token;

import jakarta.annotation.Nonnull;

import io.github.photowey.riff.infras.authentication.api.token.TokenParserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * {@code JwtTokenParserService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public interface JwtTokenParserService extends TokenParserService {

    /**
     * Parse the {@code JWT} token to {@code Claims}.
     *
     * @param token     the {@code JWT} token.
     * @param secretKey the {@code JWT} Secret Key.
     * @return the {@code Claims}.
     */
    Jws<Claims> parseClaims(@Nonnull String token, @Nonnull String secretKey);

    /**
     * Clean the {@code JWT} token.
     *
     * @param token the {@code JWT} token.
     * @return the cleaned {@code JWT} token.
     */
    default String cleanToken(@Nonnull String token) {
        if (token.contains(TOKEN_SEPARATOR)) {
            return token.split(TOKEN_SEPARATOR)[1];
        }

        return token;
    }
}
