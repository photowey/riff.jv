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
package io.github.photowey.riff.infras.exception.core.base;

import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.exception.core.domain.body.ExceptionBody;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * {@code RootException}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class RootException extends RuntimeException {

    protected Integer status;
    protected String code;
    protected String message;

    public RootException() {
        this.status = ExceptionStatus.INTERNAL_ERROR.status();
        this.code = ExceptionStatus.INTERNAL_ERROR.code();
        this.message = ExceptionStatus.INTERNAL_ERROR.message();
    }

    public RootException(String message, Object... args) {
        super(formatMessage(message, args));
        this.status = ExceptionStatus.INTERNAL_ERROR.status();
        this.code = ExceptionStatus.INTERNAL_ERROR.code();
        this.message = formatMessage(message, args);
    }

    public RootException(Throwable cause, String message, Object... args) {
        super(formatMessage(message, args), cause);
        this.status = ExceptionStatus.INTERNAL_ERROR.status();
        this.code = ExceptionStatus.INTERNAL_ERROR.code();
        this.message = formatMessage(message, args);
    }

    public RootException(Throwable cause) {
        super(cause);
        this.status = ExceptionStatus.INTERNAL_ERROR.status();
        this.code = ExceptionStatus.INTERNAL_ERROR.code();
        this.message = ExceptionStatus.INTERNAL_ERROR.message();
    }

    public RootException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = ExceptionStatus.INTERNAL_ERROR.status();
        this.code = ExceptionStatus.INTERNAL_ERROR.code();
        this.message = ExceptionStatus.INTERNAL_ERROR.message();
    }

    public RootException(ExceptionStatus status) {
        super(status.message());
        this.status = status.status();
        this.code = status.code();
        this.message = status.message();
    }

    public RootException(ExceptionStatus status, String message, Object... args) {
        super(formatMessage(message, args));
        this.status = status.status();
        this.code = status.code();
        this.message = formatMessage(message, args);
    }

    public ExceptionBody toExceptionBody() {
        return ExceptionBody.builder()
            .code(this.code())
            .message(this.message())
            .build();
    }

    // ----------------------------------------------------------------

    public static String formatMessage(String message, Object... args) {
        return StringFormatter.format(message, args);
    }
}
