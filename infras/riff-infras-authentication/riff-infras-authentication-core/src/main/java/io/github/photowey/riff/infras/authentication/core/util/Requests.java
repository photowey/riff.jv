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

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.github.photowey.riff.infras.authentication.core.wrapper.HeaderHttpServletRequestWrapper;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.common.util.Objects;

/**
 * {@code Requests}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public final class Requests {

    private Requests() {
        AssertionErrors.throwz(Requests.class);
    }

    @Nullable
    public static HttpServletRequest getRequest() {
        return getRequest(true);
    }

    @Nullable
    public static HttpServletRequest getRequest(boolean nullable) {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            if (nullable) {
                return null;
            }

            throw new NullPointerException("riff: the request[ServletRequestAttributes] is NULL.");
        }

        HttpServletRequest request = requestAttributes.getRequest();
        return checkHttpServletRequest(request);
    }

    public static void resetRequest(HttpServletRequest request) {
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    public static void resetRequest(HttpServletRequest request, HttpHeaders headers) {
        HeaderHttpServletRequestWrapper requestWrapper = new HeaderHttpServletRequestWrapper(request, headers);
        resetRequest(requestWrapper);
    }

    private static HttpServletRequest checkHttpServletRequest(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new NullPointerException("riff: the request[HttpServletRequest] is NULL.");
        }

        return request;
    }
}
