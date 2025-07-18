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
package io.github.photowey.riff.infras.exception.core.enums;

/**
 * {@code ExceptionStatus}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public enum ExceptionStatus {

    // STATUS

    OK(200, "200000", "OK"),
    BAD_REQUEST(400, "400000", "BAD_REQUEST"),
    UNAUTHORIZED_INVALID_MISSING_REQUIRED_PARAMETER(400, "400001", "MISSING_REQUIRED_PARAMETER"),

    UNAUTHORIZED(401, "401000", "UNAUTHORIZED"),
    UNAUTHORIZED_TOKEN_EXPIRED(401, "401001", "TOKEN_EXPIRED"),
    UNAUTHORIZED_INVALID_TOKEN_SIGNATURE(401, "401002", "INVALID_TOKEN_SIGNATURE"),
    UNAUTHORIZED_INVALID_TOKEN_TYPE(401, "401003", "INVALID_TOKEN_TYPE"),

    FORBIDDEN(403, "403000", "FORBIDDEN"),

    INTERNAL_ERROR(500, "500000", "INTERNAL_ERROR"),
    TIMEOUT(504, "504000", "TIMEOUT"),

    ;

    private final int status;
    private final String code;
    private final String message;

    ExceptionStatus(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ExceptionStatus resolve(int status) {
        for (ExceptionStatus ex : values()) {
            if (status == ex.status()) {
                return ex;
            }
        }

        return INTERNAL_ERROR;
    }

    public static ExceptionStatus resolve(String code) {
        for (ExceptionStatus ex : values()) {
            if (ex.code().equalsIgnoreCase(code)) {
                return ex;
            }
        }

        return INTERNAL_ERROR;
    }

    public int status() {
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
