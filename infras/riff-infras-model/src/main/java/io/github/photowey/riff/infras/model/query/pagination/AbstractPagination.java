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
package io.github.photowey.riff.infras.model.query.pagination;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractPagination}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPagination implements Pagination {

    @Serial
    private static final long serialVersionUID = 2254095122617604671L;

    @Schema(
        description = "PageNo default: 1",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        minimum = "1",
        example = "1"
    )
    protected Long pageNo;

    @Schema(
        description = "PageSize: default: 10, max: 100",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        minimum = "1",
        maximum = "100",
        example = "10"
    )
    protected Long pageSize;

    // ----------------------------------------------------------------

    @Override
    public Long pageNo() {
        return this.getPageNo();
    }

    @Override
    public Long pageSize() {
        return this.getPageSize();
    }
}
