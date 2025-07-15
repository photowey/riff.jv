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
package io.github.photowey.riff.infras.model.result.row;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code RowStruct}.
 *
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Row<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7827533504648565690L;

    @Schema(description = "The pageNo default: 1", example = "1")
    private long pageNo = 1L;
    @Schema(description = "The pageSize default: 10", example = "10")
    private long pageSize = 10L;
    @Schema(description = "The total count", example = "10086")
    private long total;

    @Schema(description = "The row data")
    private List<T> rows = new ArrayList<>(0);

    // ----------------------------------------------------------------

    public Row(List<T> rows) {
        this.total = rows.size();
        this.rows = rows;
    }

    public Row(List<T> rows, Meta meta) {
        this.pageNo = Objects.defaultIfNull(meta.pageNo(), 1L);
        this.pageSize = Objects.defaultIfNull(meta.pageSize(), 10L);
        this.total = Objects.defaultIfNull(meta.total(), 0L);

        this.rows = rows;
    }

    public Row(Long pageNo, Long pageSize, Long total) {
        this(pageNo, pageSize, total, new ArrayList<>(0));
    }

    public Row(Long pageNo, Long pageSize, Long total, List<T> rows) {
        this.pageNo = Objects.defaultIfNull(pageNo, 1L);
        this.pageSize = Objects.defaultIfNull(pageSize, 10L);
        this.total = Objects.defaultIfNull(total, 0L);

        this.rows = rows;
    }

    // ----------------------------------------------------------------

    public static <D> Row<D> create() {
        return new Row<>();
    }

    public long pageNo() {
        return pageNo;
    }

    public long pageSize() {
        return pageSize;
    }

    public long total() {
        return total;
    }

    public List<T> rows() {
        return rows;
    }

}
