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

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.common.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * {@code Responses}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public final class Responses {

    private Responses() {
        AssertionErrors.throwz(Responses.class);
    }

    @Nullable
    public static HttpServletResponse getResponse() {
        return getResponse(true);
    }

    @Nullable
    public static HttpServletResponse getResponse(boolean nullable) {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            if (nullable) {
                return null;
            }

            throw new NullPointerException("riff: the response[ServletRequestAttributes] is NULL.");
        }

        HttpServletResponse response = requestAttributes.getResponse();
        if (Objects.isNull(response)) {
            if (nullable) {
                return null;
            }

            throw new NullPointerException("riff: the response[HttpServletResponse] is NULL.");
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return response;
    }

    public static PrintWriter getPrintWriter() throws IOException {
        HttpServletResponse response = getResponse();
        checkHttpServletResponse(response);

        return getPrintWriter(Objects.requireNonNull(response));
    }

    public static PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        return response.getWriter();
    }

    public static void write(String text) {
        write(text, MediaType.APPLICATION_JSON_VALUE);
    }

    public static void write(String text, String contentType) {
        HttpServletResponse response = getResponse();
        checkHttpServletResponse(response);

        write(Objects.requireNonNull(response), text, contentType);
    }

    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        try (PrintWriter writer = getPrintWriter(response)) {
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            throw new SecurityAuthenticationException(e);
        }
    }

    private static void checkHttpServletResponse(HttpServletResponse response) {
        if (Objects.isNull(response)) {
            throw new NullPointerException("riff: the response[HttpServletResponse] is NULL.");
        }
    }
}
