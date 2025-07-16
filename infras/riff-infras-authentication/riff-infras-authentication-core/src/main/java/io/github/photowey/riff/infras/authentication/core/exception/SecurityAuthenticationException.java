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
package io.github.photowey.riff.infras.authentication.core.exception;

import io.github.photowey.riff.infras.exception.core.base.FormattableException;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

/**
 * {@code SecurityAuthenticationException}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public class SecurityAuthenticationException extends FormattableException {

    public SecurityAuthenticationException() {
        super();
    }

    public SecurityAuthenticationException(String message, Object... args) {
        super(message, args);
    }

    public SecurityAuthenticationException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public SecurityAuthenticationException(Throwable cause) {
        super(cause);
    }

    public SecurityAuthenticationException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    // ----------------------------------------------------------------

    public SecurityAuthenticationException(ExceptionStatus status) {
        super(status);
    }

    public SecurityAuthenticationException(ExceptionStatus status, String message, Object... args) {
        super(status, message, args);
    }
}
