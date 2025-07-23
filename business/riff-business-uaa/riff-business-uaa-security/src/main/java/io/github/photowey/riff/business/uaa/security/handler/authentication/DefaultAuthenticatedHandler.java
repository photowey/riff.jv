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
package io.github.photowey.riff.business.uaa.security.handler.authentication;

import java.util.function.Function;

import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.authentication.core.util.Securitys;
import io.github.photowey.riff.infras.authentication.jjwt.engine.AuthenticationEngine;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractApplicationContextHolder;

/**
 * {@code DefaultAuthenticatedHandler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public class DefaultAuthenticatedHandler extends AbstractApplicationContextHolder implements AuthenticatedHandler {

    private final AuthenticationEngine authenticationEngine;

    public DefaultAuthenticatedHandler(AuthenticationEngine authenticationEngine) {
        this.authenticationEngine = authenticationEngine;
    }

    public AuthenticationEngine auth() {
        return authenticationEngine;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean supports(String requestPath) {
        return true;
    }

    @Override
    public void authenticated() {
        LoginUser dummy = Securitys.tryAcquireLoginUser();
        if (LoginUser.determineIsAuthenticated(dummy) || this.isAuthenticated()) {
            return;
        }

        String token = Securitys.parseAuthorizationHeader();
        this.checkAuthed(token);
        this.handle(token);

        this.checkUnAuthed();
    }

    @Override
    public void tryAuthenticated() {
        LoginUser dummy = Securitys.tryAcquireLoginUser();
        if (LoginUser.determineIsAuthenticated(dummy) || this.isAuthenticated()) {
            return;
        }

        String authorizationToken = Securitys.parseAuthorizationHeader();
        if (Strings.isNotEmpty(authorizationToken)) {
            this.handleQuiet(authorizationToken);
        }

        this.tryCheckUnAuthed();
    }

    public void handle(String token) {
        this.validateToken(token);
        this.authenticationEngine.jwt().tryAuthentication(token);
    }

    public void handleQuiet(String authToken) {
        try {
            this.handle(authToken);
        } catch (Exception ignored) {
            // ignored
        }
    }

    protected boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.isNotNull(authentication) && authentication.isAuthenticated();
    }

    protected void validateToken(String authToken) {
        this.validateToken(authToken, false);
    }

    protected void validateToken(String authToken, boolean quiet) {
        if (Strings.isEmpty(authToken)
            || !this.authenticationEngine.jwt().validateToken(authToken, quiet)) {
            throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
        }
    }

    protected void checkUnAuthed() {
        this.doCheckUnAuthed(AuthenticationPrincipal::determineIsForbiddenRequest);
    }

    protected void tryCheckUnAuthed() {
        this.doCheckUnAuthed(AuthenticationPrincipal::tryDetermineIsForbiddenRequest);
    }

    protected void doCheckUnAuthed(Function<AuthenticationPrincipal, Boolean> fx) {
        LoginUser loginUser = LoginUserHolder.get();
        if (Objects.isNotNull(loginUser)) {
            AuthenticationPrincipal principal = this.tryLoadAuthenticatedPrincipal(loginUser);

            if (fx.apply(principal)) {
                throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
            }
        }

        // ...
        // TODO Not implemented
    }

    private AuthenticationPrincipal tryLoadAuthenticatedPrincipal(LoginUser loginUser) {
        // TODO Not implemented
        throw new UnsupportedOperationException("Not implemented");
    }

    protected void checkAuthed(String token) {
        if (Strings.isEmpty(token)) {
            throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
        }
    }
}
