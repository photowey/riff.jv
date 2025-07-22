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

import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;

/**
 * {@code SaveStorage}.
 *
 * @param <T> The {@code Database} entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface SaveStorage<T extends Entity> {

    void save(@Nonnull T entity);

    void batchSave(@Nonnull Collection<T> entities);

    default <P extends Entity> void copyBase(@Nonnull T entity, @Nonnull P po) {
        entity.setId(po.getId());

        if (Objects.isNull(entity.getCreateBy())) {
            entity.setCreateBy(po.getCreateBy());
        }
        if (Objects.isNull(entity.getUpdateBy())) {
            entity.setUpdateBy(po.getUpdateBy());
        }

        if (Objects.isNull(entity.getCreateTime())) {
            entity.setCreateTime(po.getCreateTime());
        }

        if (Objects.isNull(entity.getUpdateTime())) {
            entity.setUpdateTime(po.getUpdateTime());
        }

        this.copyTenant(entity, po);
        this.copyExt(entity, po);
    }

    default <P extends Entity> void copyTenant(@Nonnull T entity, @Nonnull P po) {
        if (po instanceof AbstractTenantEntity ptt) {
            AbstractTenantEntity tt = (AbstractTenantEntity) entity;
            tt.setTenant(ptt.tenant());
            tt.setPlatform(ptt.platform());
            tt.setApp(ptt.app());
        }
    }

    default <P extends Entity> void copyExt(@Nonnull T entity, @Nonnull P po) {
        if (po instanceof AbstractEntityExt ett) {
            AbstractEntityExt tt = (AbstractEntityExt) entity;
            tt.setVersion(ett.version());
            tt.setDeleted(ett.deleted());
        }
    }
}
