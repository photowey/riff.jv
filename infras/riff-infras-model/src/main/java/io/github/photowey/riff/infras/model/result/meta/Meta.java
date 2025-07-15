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
package io.github.photowey.riff.infras.model.result.meta;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.photowey.riff.infras.model.constant.PaginationConstants;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Meta}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meta implements Serializable {

    @Serial
    private static final long serialVersionUID = -553396062548580887L;

    @Schema(
        description = "The page no, default: 1",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1"
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pageNo;

    @Schema(
        description = "The page size, default: 10",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "10"
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pageSize;

    @Schema(
        description = "The total count",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "10086"
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private Long total;

    @JsonIgnore
    @Schema(
        description = "The total pages",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "8848"
    )
    @JsonSerialize(using = ToStringSerializer.class)
    private Long totalPages;

    // ----------------------------------------------------------------

    public Long pageNo() {
        return pageNo;
    }

    public Long pageSize() {
        return pageSize;
    }

    public Long total() {
        return total;
    }

    public Long totalPages() {
        return totalPages;
    }

    // ----------------------------------------------------------------

    public static Meta populateDefaultMeta() {
        return populateMeta(PaginationConstants.DEFAULT_PAGE_SIZE);
    }

    public static <D> Meta populateMeta(PageResult<D> page) {
        return Meta.builder()
            .pageNo(page.data().pageNo())
            .pageSize(page.data().pageSize())
            .total(page.data().total())
            .build();
    }

    public static Meta populateMeta(AbstractPaginationQuery<?> query) {
        return Meta.populateMeta(query.total(), query.pageNo(), query.pageSize());
    }

    public static Meta populateMeta(long pageSize) {
        return populateMeta(
            PaginationConstants.DEFAULT_TOTAL_COUNT,
            PaginationConstants.DEFAULT_PAGE_NO,
            pageSize
        );
    }

    public static Meta populateMeta(long total, long pageNo, long pageSize) {
        return Meta.builder()
            .pageNo(pageNo)
            .pageSize(pageSize)
            .total(total)
            .totalPages(getPages(total, pageSize))
            .build();
    }

    private static long getPages(long total, long pageSize) {
        if (pageSize == 0L) {
            return 0L;
        } else {
            long pages = total / pageSize;
            if (total % pageSize != 0L) {
                ++pages;
            }

            return pages;
        }
    }
}
