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
package io.github.photowey.riff.infras.exception.core.domain.body;

import java.io.Serial;
import java.io.Serializable;

import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.util.Arrays;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ExceptionBody}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -3668039419501075434L;

    private static final String DEFAULT_MESSAGE =
        "The server is taking a little break. Please click to refresh and try again!";

    private String code;
    private String message;

    public ExceptionBody(ExceptionStatus status) {
        this.code = status.code();
        this.message = status.message();
    }

    public ExceptionBody(ExceptionStatus status, String message) {
        this.code = status.code();
        this.message = message;
    }

    // ----------------------------------------------------------------

    public static class ExceptionBodyBuilder {
        private String code;
        private String message;

        ExceptionBodyBuilder() {
        }

        public ExceptionBodyBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ExceptionBodyBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ExceptionBody build() {
            return new ExceptionBody(this.code, this.message);
        }

        @Override
        public String toString() {
            return "ExceptionBody.ExceptionBodyBuilder(code="
                + this.code
                + ", message="
                + this.message
                + ")";
        }
    }

    // ----------------------------------------------------------------

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    // ----------------------------------------------------------------

    public static ExceptionBody badRequest() {
        return new ExceptionBody(ExceptionStatus.BAD_REQUEST);
    }

    public static ExceptionBody badRequest(String message, Object... args) {
        return new ExceptionBody(
            ExceptionStatus.BAD_REQUEST,
            StringFormatter.format(message, args)
        );
    }

    public static ExceptionBody badUnHandle() {
        return new ExceptionBody(ExceptionStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public static ExceptionBody unauthorized() {
        return new ExceptionBody(ExceptionStatus.UNAUTHORIZED);
    }

    public static ExceptionBody unauthorized(String message) {
        return new ExceptionBody(ExceptionStatus.UNAUTHORIZED, message);
    }

    public static ExceptionBody forbidden() {
        return new ExceptionBody(ExceptionStatus.FORBIDDEN);
    }

    public static ExceptionBody forbidden(String message) {
        return new ExceptionBody(ExceptionStatus.FORBIDDEN, message);
    }

    public static ExceptionBody nullPointer() {
        return new ExceptionBody(ExceptionStatus.INTERNAL_ERROR, "NULL pointer");
    }

    public static ExceptionBody illegalArgument() {
        return new ExceptionBody(ExceptionStatus.INTERNAL_ERROR, "illegal arguments");
    }

    public static ExceptionBody unsupportedOperation() {
        return new ExceptionBody(ExceptionStatus.INTERNAL_ERROR, "unsupported operation");
    }

    public static ExceptionBody runtime(String... args) {
        String message = DEFAULT_MESSAGE;
        if (isNotEmpty(args) && isNotEmpty(args[0])) {
            message = args[0];
        }

        return new ExceptionBody(ExceptionStatus.INTERNAL_ERROR, message);
    }

    public static ExceptionBody timeout() {
        return new ExceptionBody(ExceptionStatus.TIMEOUT);
    }

    public static boolean isNotEmpty(String[] sources) {
        return Arrays.isNotEmpty(sources);
    }

    public static boolean isNotEmpty(String source) {
        return Strings.isNotEmpty(source);
    }
}

