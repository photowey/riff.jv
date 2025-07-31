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

import io.github.photowey.riff.core.domain.entity.ScheduleJobTriggerStat;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobTriggerStatPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleJobTriggerStatRepository;
import io.github.photowey.riff.storage.api.ScheduleJobTriggerStatStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleJobTriggerStatAssembler;

/**
 * {@code ScheduleJobTriggerStatStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class ScheduleJobTriggerStatStorageImpl
    implements ScheduleJobTriggerStatStorage<ScheduleJobTriggerStatPO>,
    PaginationMetaStorage<ScheduleJobTriggerStatPO> {

    @Autowired
    private ScheduleJobTriggerStatRepository scheduleJobTriggerStatRepository;

    @Autowired
    private ScheduleJobTriggerStatAssembler scheduleJobTriggerStatAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleJobTriggerStat, ScheduleJobTriggerStatPO> entityAssembler() {
        return this.scheduleJobTriggerStatAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleJobTriggerStat entity) {
        ScheduleJobTriggerStatPO po = this.toPo(entity);
        this.scheduleJobTriggerStatRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleJobTriggerStat> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<ScheduleJobTriggerStat> images = new ArrayList<>(entities);
        List<ScheduleJobTriggerStatPO> pos = this.toPos(images);
        this.scheduleJobTriggerStatRepository.batchInserts(pos, ScheduleJobTriggerStatPO.class);

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
    public void delete(@Nonnull ScheduleJobTriggerStat entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id is required.");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.scheduleJobTriggerStatRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.scheduleJobTriggerStatRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleJobTriggerStat entity) {
        this.scheduleJobTriggerStatRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleJobTriggerStat selectOne(@Nonnull Long id) {
        return this.toEntity(this.scheduleJobTriggerStatRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleJobTriggerStat>> List<ScheduleJobTriggerStat> selectList(
        @Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleJobTriggerStat>> List<ScheduleJobTriggerStat> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull ScheduleJobTriggerStat entity, @Nonnull P po) {
        ScheduleJobTriggerStatStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull ScheduleJobTriggerStat entity, @Nonnull P po) {
        ScheduleJobTriggerStatStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
