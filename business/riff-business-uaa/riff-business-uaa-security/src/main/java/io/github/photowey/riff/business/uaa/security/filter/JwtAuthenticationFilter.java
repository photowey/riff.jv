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
package io.github.photowey.riff.business.uaa.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import io.github.photowey.riff.business.uaa.security.handler.authentication.AuthenticatedHandler;
import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.authentication.core.util.Responsers;
import io.github.photowey.riff.infras.authentication.jjwt.engine.AuthenticationEngine;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code JwtAuthenticationFilter}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationFilter {

    private final AuthenticationEngine auth;

    public JwtAuthenticationFilter(AuthenticationEngine auth) {
        this.auth = auth;
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return this.determineIsWebSocketRequest(request);
    }

    @Override
    public void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        if (this.determineIsUnAuthenticationRequest(request)) {
            Responsers.doResponse(ExceptionStatus.UNAUTHORIZED_INVALID_TOKEN_TYPE);
            return;
        }

        this.tryCleanPreviousAuthentication();
        this.tryAuthenticationRequest(request, response, filterChain, request);
    }

    private boolean determineIsUnAuthenticationRequest(HttpServletRequest request) {
        // TODO NOT implemented
        return false;
    }

    private void tryCleanPreviousAuthentication() {
        LoginUserHolder.clean();
    }

    private void tryAuthenticationRequest(
        ServletRequest request, ServletResponse response,
        FilterChain filterChain, HttpServletRequest httpRequest) throws IOException, ServletException {
        String requestPath = this.auth.guard().getLookupPathForRequest(httpRequest);
        String method = httpRequest.getMethod().toUpperCase();

        try {
            this.tryFilter(request, response, filterChain, httpRequest, requestPath);
        } catch (SecurityAuthenticationException e) {
            reportCustom(e, method, requestPath);

            Responsers.doResponse(ExceptionStatus.UNAUTHORIZED, e.getMessage());
        } catch (Throwable e) {
            reportThrowable(e, method, requestPath);

            throw e;
        }
    }

    private void tryFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain filterChain,
        HttpServletRequest httpRequest,
        String requestPath) throws IOException, ServletException {
        if (this.auth.guard().determineIsAuthenticationRequest(httpRequest)) {
            this.onAuthenticationPathRequest(requestPath);
        } else {
            this.onIgnorePathRequest(requestPath);
        }

        this.checkScopes(requestPath);

        filterChain.doFilter(request, response);
    }

    private void checkScopes(String requestPath) {
        this.check2faScope(requestPath);
    }

    private void check2faScope(String requestPath) {
        // TODO NOT implemented
    }

    private void onAuthenticationPathRequest(String requestPath) {
        this.handleAuthenticationRequest(requestPath, AuthenticatedHandler::authenticated);
    }

    private void onIgnorePathRequest(String requestPath) {
        this.tryOnAuthenticationRequestIfNecessary(requestPath);
    }

    private void tryOnAuthenticationRequestIfNecessary(String requestPath) {
        this.handleAuthenticationRequest(requestPath, AuthenticatedHandler::tryAuthenticated);
    }

    private void handleAuthenticationRequest(String requestPath, Consumer<AuthenticatedHandler> fx) {
        Map<String, AuthenticatedHandler> beans =
            this.listableBeanFactory().getBeansOfType(AuthenticatedHandler.class);

        List<AuthenticatedHandler> handlers = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(handlers);

        for (AuthenticatedHandler handler : handlers) {
            if (handler.supports(requestPath)) {
                fx.accept(handler);
                break;
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }

    // ----------------------------------------------------------------

    private static void reportThrowable(Throwable e, String method, String requestPath) {
        log.error("uaa: Unknown exception occurred: request:[{} {}]",
            method,
            requestPath,
            e
        );
    }

    private static void reportCustom(SecurityAuthenticationException e, String method, String requestPath) {
        log.error("uaa: Security authentication exception occurred. request:[{} {}]",
            method,
            requestPath,
            e
        );
    }
}
