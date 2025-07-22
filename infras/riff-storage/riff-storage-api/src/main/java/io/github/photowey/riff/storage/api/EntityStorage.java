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
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import io.github.photowey.riff.core.domain.table.TableId;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;

/**
 * {@code EntityStorage}.
 *
 * @param <T>  The {@code Database} entity type.
 * @param <PO> The {@code Database} persistence entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface EntityStorage<T extends TableId, PO>
    extends QueryStorage<T>, DeleteStorage<T>, UpdateStorage<T>, SaveStorage<T> {

    default EntityAssembler<T, PO> entityAssembler() {
        return null;
    }

    /**
     * Convert {@code Database} entity to {@code Database} persistence entity.
     *
     * @param entity the {@code Database} entity.
     * @return the {@code Database} persistence entity.
     */
    default PO toPo(@Nullable T entity) {
        if (Objects.isNull(this.entityAssembler()) || Objects.isNull(entity)) {
            return null;
        }

        return this.entityAssembler().toEntity(entity);
    }

    default List<PO> toPos(@Nonnull Collection<T> entities) {
        if (Collections.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream()
            .map(this::toPo)
            .collect(Collectors.toList());
    }

    default T toEntity(@Nullable PO po) {
        if (Objects.isNull(this.entityAssembler()) || Objects.isNull(po)) {
            return null;
        }

        return this.entityAssembler().toDto(po);
    }

    default List<T> toEntities(@Nonnull Collection<PO> pos) {
        if (Collections.isEmpty(pos)) {
            return Collections.emptyList();
        }

        return pos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}
