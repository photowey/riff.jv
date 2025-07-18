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

import io.github.photowey.riff.infras.model.constant.PaginationConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractPaginationQuery}.
 *
 * @param <T> The Database Entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractPaginationQuery<T> extends AbstractPagination implements PaginationQuery {

    @Schema(hidden = true)
    private Long total;

    // ----------------------------------------------------------------

    @Override
    @Schema(hidden = true)
    public Integer getLimit() {
        return this.getPageSize().intValue();
    }

    @Override
    @Schema(hidden = true)
    public Integer getOffset() {
        return (int) (this.start() * this.getPageSize());
    }

    @Schema(hidden = true)
    public Long getTotal() {
        return null != this.total ? this.total : PaginationConstants.DEFAULT_TOTAL_COUNT;
    }

    // ----------------------------------------------------------------

    @Override
    public void selectOne() {
        this.selectPage(
            PaginationConstants.DEFAULT_PAGE_NO,
            PaginationConstants.MIN_PAGE_SIZE_THRESHOLD
        );
    }

    @Override
    public void selectPage(long pageNo, long pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    // ----------------------------------------------------------------

    @Override
    public Long getPageNo() {
        if (null == this.pageNo) {
            return PaginationConstants.DEFAULT_PAGE_NO;
        }
        if (this.pageNo < PaginationConstants.DEFAULT_PAGE_NO) {
            return PaginationConstants.DEFAULT_PAGE_NO;
        }

        return this.pageNo;
    }

    @Override
    public Long getPageSize() {
        if (null == this.pageSize) {
            return PaginationConstants.DEFAULT_PAGE_SIZE;
        }

        if (this.pageSize > PaginationConstants.MAX_PAGE_SIZE_THRESHOLD) {
            return PaginationConstants.MAX_PAGE_SIZE_THRESHOLD;
        }

        if (this.pageSize < PaginationConstants.MIN_PAGE_SIZE_THRESHOLD) {
            return PaginationConstants.MIN_PAGE_SIZE_THRESHOLD;
        }

        return this.pageSize;
    }

    // ----------------------------------------------------------------

    public long start() {
        return this.getPageNo() - PaginationConstants.DEFAULT_PAGE_NO;
    }

    // ----------------------------------------------------------------

    public Integer limit() {
        return this.getLimit();
    }

    public Integer offset() {
        return this.getOffset();
    }

    public Long total() {
        return this.getTotal();
    }
}
