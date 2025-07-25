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
package io.github.photowey.riff.storage.api;

import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.PageResult;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.table.TableId;

/**
 * {@code QueryStorage}.
 *
 * @param <T> The {@code Database} entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface QueryStorage<T extends TableId> {

    /**
     * Select One.
     *
     * @param id The id.
     * @return The entity.
     */
    @Nullable
    T selectOne(@Nonnull Long id);

    /**
     * Select List.
     *
     * @param query The query.
     * @param <Q>   The query type.
     * @return The list of entities.
     */
    @Nonnull
    <Q extends AbstractQuery<T>> List<T> selectList(@Nonnull Q query);

    /**
     * Select Page.
     *
     * @param query The query.
     * @param fx    The meta function.
     * @param <Q>   The query type.
     * @return The list of entities.
     */
    @Nonnull
    <Q extends AbstractPaginationQuery<T>> List<T> selectPage(@Nonnull Q query, Consumer<Meta> fx);

    /**
     * Select Page.
     *
     * @param query The query.
     * @param <Q>   The query type.
     * @return The page result.
     */
    @Nonnull
    default <Q extends AbstractPaginationQuery<T>> PageResult<T> selectPage(@Nonnull Q query) {
        PageResult<T> result = PageResult.empty();
        List<T> systemUsers = this.selectPage(query, (meta) -> {
            result.data().resetMeta(meta);
        });

        result.data().resetRows(systemUsers);

        return result;
    }
}
