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
import java.util.List;
import java.util.Map;

import io.github.photowey.riff.infras.model.enums.ApiDictionary;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.infras.model.result.row.Row;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@code PageResult}.
 *
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends AbstractResult<T, PageResult<T>> {

    @Serial
    private static final long serialVersionUID = 8516482956446374018L;

    @Schema(description = "The row data.")
    private Row<T> data;

    @Schema(description = "The additional data.")
    private Map<String, Object> additional = new HashMap<>();

    // ----------------------------------------------------------------

    public PageResult() {
        this(
            ApiDictionary.Status.API_OK.value(),
            ApiDictionary.Status.API_OK.message()
        );
    }

    protected PageResult(String code, String message) {
        super(code, message);
    }

    protected PageResult(String code, String message, List<T> rows, Meta meta) {
        super(code, message);
        this.data = new Row<>(rows, meta);
    }

    // ----------------------------------------------------------------

    public static <T> PageResult<T> create() {
        return new PageResult<>();
    }

    // ----------------------------------------------------------

    public static <T> PageResult<T> empty() {
        PageResult<T> result = create();
        Row<T> struct = Row.create();
        result.setData(struct);

        return result;
    }

    // ----------------------------------------------------------------

    public Row<T> data() {
        return data;
    }

    public Map<String, Object> additional() {
        return additional;
    }
}
