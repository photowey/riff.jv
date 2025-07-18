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
package io.github.photowey.riff.infras.model.result;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.github.photowey.riff.infras.model.constant.ApiConstants;
import io.github.photowey.riff.infras.model.enums.ApiDictionary;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@code AbstractResult}.
 *
 * @param <R> The Result type.
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@AllArgsConstructor
public abstract class AbstractResult
    <T, R extends AbstractResult<T, R>> implements Serializable {

    @Schema(
        description = "The return code 200: success",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "401000"
    )
    protected String code;

    @Schema(
        description = "The return description message",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "OK"
    )
    protected String message;

    @Schema(
        description = "The Server current time",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1741660057000"
    )
    private LocalDateTime now;

    public AbstractResult() {
        this(
            ApiDictionary.Status.API_OK.value(),
            ApiDictionary.Status.API_OK.message(),
            LocalDateTime.now()
        );
    }

    protected AbstractResult(String code, String message) {
        this(code, message, LocalDateTime.now());
    }

    // ----------------------------------------------------------------

    public String code() {
        return code;
    }

    public R code(String code) {
        this.code = code;
        return (R) this;
    }

    public String message() {
        return message;
    }

    public R message(String message) {
        this.message = message;
        return (R) this;
    }

    public LocalDateTime now() {
        return now;
    }

    public R setNow(LocalDateTime now) {
        this.now = now;
        return (R) this;
    }

    // ----------------------------------------------------------------

    public boolean predicateIsSuccessful() {
        return this.requestSuccessful();
    }

    public boolean predicateIsFailed() {
        return this.requestFailure();
    }

    public boolean requestSuccessful() {
        return ApiConstants.API_OK.equalsIgnoreCase(this.code);
    }

    public boolean requestFailure() {
        return !this.requestSuccessful();
    }
}

