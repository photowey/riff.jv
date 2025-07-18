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

import java.util.function.Consumer;

import jakarta.annotation.Nonnull;

import org.springframework.security.core.Authentication;

import io.github.photowey.riff.infras.authentication.api.token.TokenService;
import io.github.photowey.riff.infras.authentication.jjwt.token.context.DefaultTokenContext;
import io.github.photowey.riff.infras.authentication.jjwt.token.context.TokenContext;
import io.jsonwebtoken.JwtBuilder;

/**
 * {@code JwtTokenService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public interface JwtTokenService extends JwtTokenParserService, TokenService {

    /**
     * Create {@code JWT} token.
     *
     * @param ctx the {@code TokenContext}.
     * @return the {@code JWT} token.
     */
    String createToken(@Nonnull TokenContext ctx);

    default String createToken(@Nonnull Authentication authentication, @Nonnull Consumer<JwtBuilder> fx) {
        return this.createToken(authentication, false, fx);
    }

    default String createToken(
        @Nonnull Authentication authentication, boolean rememberMe, @Nonnull Consumer<JwtBuilder> fx) {
        TokenContext ctx = DefaultTokenContext.builder()
            .authentication(authentication)
            .rememberMe(rememberMe)
            .callback(fx)
            .now(System.currentTimeMillis())
            .build();

        return this.createToken(ctx);
    }

    // ----------------------------------------------------------------

    /**
     * Create {@code JWT} refresh token.
     *
     * @param ctx the {@code TokenContext}.
     * @return the {@code JWT} refresh token.
     */
    String createRefreshToken(@Nonnull TokenContext ctx);

    /**
     * Create {@code JWT} RefreshToken.
     *
     * @param authentication the {@code Authentication}.
     * @param fx             the {@code JwtBuilder} callback.
     * @return the {@code JWT} refresh token.
     */
    default String createRefreshToken(@Nonnull Authentication authentication, @Nonnull Consumer<JwtBuilder> fx) {
        TokenContext ctx = DefaultTokenContext.builder()
            .authentication(authentication)
            .rememberMe(false)
            .callback(fx)
            .now(System.currentTimeMillis())
            .build();

        return this.createRefreshToken(ctx);
    }
}
