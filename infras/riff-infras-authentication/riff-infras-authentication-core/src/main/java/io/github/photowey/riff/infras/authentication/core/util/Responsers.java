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

import org.springframework.http.MediaType;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.common.json.JSON;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.exception.core.domain.body.ExceptionBody;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

/**
 * {@code Responser}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@SuppressWarnings("all")
public final class Responsers {

    private Responsers() {
        AssertionErrors.throwz(Responsers.class);
    }

    public static void doResponse(String body) {
        write(body);
    }

    public static <T> void doResponse(T body) {
        write(body);
    }

    public static <T> void doResponse(SecurityAuthenticationException exception) {
        write(new ExceptionBody(exception.code(), exception.message()));
    }

    public static void doResponse(ExceptionStatus status) {
        write(new ExceptionBody(status));
    }

    public static void doResponse(ExceptionStatus status, String message) {
        write(new ExceptionBody(status, message));
    }

    public static <T> void write(T body) {
        write(JSON.toJSONString(body));
    }

    public static void write(String data) {
        write(data, MediaType.APPLICATION_JSON_VALUE);
    }

    public static void write(String data, String contentType) {
        Responses.write(data, contentType);
    }
}
