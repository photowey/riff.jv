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

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.model.enums.ApiDictionary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@code ApiResult}.
 *
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiResult<T> extends AbstractResult<T, ApiResult<T>> {

    @Serial
    private static final long serialVersionUID = -4533422073973203165L;

    @Schema(description = "The data.")
    private T data;

    @Schema(description = "The additional data.")
    private Map<String, Object> additional = new HashMap<>();

    // ----------------------------------------------------------------

    public ApiResult() {
        this(
            ApiDictionary.Status.API_OK.value(),
            ApiDictionary.Status.API_OK.message()
        );
    }

    protected ApiResult(String code, String message) {
        super(code, message);
    }

    public ApiResult(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public ApiResult(T data, Map<String, Object> additional) {
        super();
        this.data = data;
        this.additional = additional;
    }

    public ApiResult(String code, String message, T data, Map<String, Object> additional) {
        super(code, message);
        this.data = data;
        this.additional = additional;
    }

    // ----------------------------------------------------------------

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(
            ApiDictionary.Status.API_OK.value(),
            ApiDictionary.Status.API_OK.message(),
            data
        );
    }

    public static <T> ApiResult<T> success(T data, Map<String, Object> additional) {
        return success(data, () -> additional);
    }

    public static <T> ApiResult<T> success(T data, Supplier<Map<String, Object>> fx) {
        return new ApiResult<>(
            ApiDictionary.Status.API_OK.value(),
            ApiDictionary.Status.API_OK.message(),
            data,
            fx.get()
        );
    }

    // ----------------------------------------------------------------

    public static <T> ApiResult<T> failed() {
        return new ApiResult<>(
            ApiDictionary.Status.API_FAILED.value(),
            ApiDictionary.Status.API_FAILED.message()
        );
    }

    public static <T> ApiResult<T> failed(String message, Object... args) {
        return new ApiResult<>(
            ApiDictionary.Status.API_FAILED.value(),
            StringFormatter.format(message, args)
        );
    }

    public static <T> ApiResult<T> failed(ApiDictionary.Status status) {
        return new ApiResult<>(
            status.value(),
            status.message()
        );
    }

    // ----------------------------------------------------------------

    public T data() {
        return data;
    }

    public Map<String, Object> additional() {
        return additional;
    }
}
