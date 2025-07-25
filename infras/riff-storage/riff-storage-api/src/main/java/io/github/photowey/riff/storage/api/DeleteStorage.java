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

import java.util.Collection;

import jakarta.annotation.Nonnull;

import io.github.photowey.riff.middleware.database.core.domain.table.TableId;

/**
 * {@code DeleteStorage}.
 *
 * @param <T> The {@code Database} entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface DeleteStorage<T extends TableId> {

    /**
     * Delete the entity.
     *
     * @param entity The entity to delete.
     */
    void delete(@Nonnull T entity);

    /**
     * Delete the entity by id.
     *
     * @param id The id of the entity to delete.
     */
    void deleteById(@Nonnull Long id);

    /**
     * Delete multiple entities by id.
     *
     * @param ids The ids of the entities to delete.
     */
    void batchDelete(@Nonnull Collection<Long> ids);
}
