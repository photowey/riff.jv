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
package io.github.photowey.riff.infras.authentication.core.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

/**
 * {@code Securitys}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public final class Securitys {

    private static final String SPACE = " ";

    private Securitys() {
        AssertionErrors.throwz(Securitys.class);
    }

    public static String parseAuthorizationHeader() {
        return parseAuthorizationHeader(
            AuthorityConstants.AUTHORIZATION_HEADER,
            AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER
        );
    }

    public static String parseAuthorizationHeader(@Nonnull String header, @Nonnull String prefix) {
        HttpServletRequest request = Requests.getRequest();
        String authorization = Objects.requireNonNull(request).getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        if (authorization.startsWith(prefix + SPACE)
            || authorization.startsWith(prefix.toLowerCase() + SPACE)) {
            return authorization.substring(prefix.length() + 1).trim();
        }

        return null;
    }

    public static String parseAuthorizationHeader(@Nonnull String header) {
        HttpServletRequest request = Requests.getRequest();
        String authorization = Objects.requireNonNull(request).getHeader(header);
        if (Strings.isEmpty(authorization)) {
            return null;
        }

        return authorization;
    }

    public static Authentication tryAcuireAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.isNull(context)) {
            return null;
        }

        return context.getAuthentication();
    }

    @Nullable
    public static LoginUser tryAcquireLoginUser() {
        Authentication authentication = tryAcuireAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }

        return tryAcquireLoginUser(authentication);
    }

    public static LoginUser tryAcquireLoginUser(@Nullable Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return null;
        }

        return authentication.getPrincipal() instanceof LoginUser
            ? (LoginUser) authentication.getPrincipal()
            : null;
    }

    public static LoginUser tryAcquireMustLoginUser() {
        Authentication authentication = tryAcuireAuthentication();
        if (Objects.isNull(authentication)) {
            LoginUser loginUser = LoginUserHolder.get();
            if (Objects.isNull(loginUser)) {
                throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
            }

            return loginUser;
        }

        return tryAcquireLoginUser(authentication);
    }

    public static Long tryAcquireUserId() {
        LoginUser loginUser = tryAcquireLoginUser();
        return Objects.isNotNull(loginUser) ? loginUser.getUserId() : null;
    }

    public static void authenticated(@Nonnull LoginUser loginUser) {
        Authentication authentication = buildAuthentication(loginUser);
        authenticated(authentication);
    }

    public static void authenticated(@Nonnull LoginUser loginUser, @Nullable Object credentials) {
        Authentication authentication = buildAuthentication(loginUser, credentials);
        authenticated(authentication);
    }

    public static void authenticated(@Nonnull Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Authentication buildAuthentication(@Nonnull LoginUser loginUser) {
        return buildAuthentication(loginUser, loginUser.getToken());
    }

    private static Authentication buildAuthentication(@Nonnull LoginUser loginUser, @Nullable Object credentials) {
        HttpServletRequest request = Requests.getRequest();
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginUser, credentials,
                loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }
}

