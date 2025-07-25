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
package io.github.photowey.riff.storage.orm.mybatis.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.photowey.riff.core.domain.entity.ScheduleClient;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleClientPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleClientRepository;
import io.github.photowey.riff.storage.api.ScheduleClientStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleClientAssembler;

/**
 * {@code ScheduleClientStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Component
public class ScheduleClientStorageImpl
    implements ScheduleClientStorage<ScheduleClientPO>, PaginationMetaStorage<ScheduleClientPO> {

    @Autowired
    private ScheduleClientRepository scheduleClientRepository;

    @Autowired
    private ScheduleClientAssembler scheduleClientAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleClient, ScheduleClientPO> entityAssembler() {
        return this.scheduleClientAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleClient entity) {
        ScheduleClientPO po = this.toPo(entity);
        this.scheduleClientRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleClient> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<ScheduleClient> images = new ArrayList<>(entities);
        List<ScheduleClientPO> pos = this.toPos(images);
        this.scheduleClientRepository.batchInserts(pos, ScheduleClientPO.class);

        for (int i = 0; i < pos.size(); i++) {
            this.copyBase(images.get(i), pos.get(i));
        }

        images.clear();
        pos.clear();

        images = null;
        pos = null;
    }

    // ----------------------------------------------------------------

    @Override
    public void delete(@Nonnull ScheduleClient entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id can't be NULL");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.scheduleClientRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.scheduleClientRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleClient entity) {
        this.scheduleClientRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleClient selectOne(@Nonnull Long id) {
        return this.toEntity(this.scheduleClientRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleClient>> List<ScheduleClient> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleClient>> List<ScheduleClient> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull ScheduleClient entity, @Nonnull P po) {
        ScheduleClientStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull ScheduleClient entity, @Nonnull P po) {
        ScheduleClientStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
