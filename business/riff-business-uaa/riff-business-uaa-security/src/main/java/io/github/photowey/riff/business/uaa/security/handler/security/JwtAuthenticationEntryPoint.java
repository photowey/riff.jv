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
package io.github.photowey.riff.business.uaa.security.handler.security;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import io.github.photowey.riff.infras.authentication.core.util.Responsers;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.exception.core.base.FormattableException;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code JwtAuthenticationEntryPoint}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        if (Objects.isNotNull(authException.getCause())
            && authException.getCause() instanceof FormattableException exception) {
            Responsers.doResponse(exception.toExceptionBody());

            return;
        }

        Responsers.doResponse(ExceptionStatus.UNAUTHORIZED);
    }
}

