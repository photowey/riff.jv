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

import io.github.photowey.riff.core.domain.entity.ScheduleJobTriggerRecord;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobTriggerRecordPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleJobTriggerRecordRepository;
import io.github.photowey.riff.storage.api.ScheduleJobTriggerRecordStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleJobTriggerRecordAssembler;

/**
 * {@code ScheduleJobTriggerRecordStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class ScheduleJobTriggerRecordStorageImpl
    implements ScheduleJobTriggerRecordStorage<ScheduleJobTriggerRecordPO>,
    PaginationMetaStorage<ScheduleJobTriggerRecordPO> {

    @Autowired
    private ScheduleJobTriggerRecordRepository scheduleJobTriggerRecordRepository;

    @Autowired
    private ScheduleJobTriggerRecordAssembler scheduleJobTriggerRecordAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleJobTriggerRecord, ScheduleJobTriggerRecordPO> entityAssembler() {
        return this.scheduleJobTriggerRecordAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleJobTriggerRecord entity) {
        ScheduleJobTriggerRecordPO po = this.toPo(entity);
        this.scheduleJobTriggerRecordRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleJobTriggerRecord> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<ScheduleJobTriggerRecord> images = new ArrayList<>(entities);
        List<ScheduleJobTriggerRecordPO> pos = this.toPos(images);
        this.scheduleJobTriggerRecordRepository.batchInserts(pos, ScheduleJobTriggerRecordPO.class);

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
    public void delete(@Nonnull ScheduleJobTriggerRecord entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id is required.");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.scheduleJobTriggerRecordRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.scheduleJobTriggerRecordRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleJobTriggerRecord entity) {
        this.scheduleJobTriggerRecordRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleJobTriggerRecord selectOne(@Nonnull Long id) {
        return this.toEntity(this.scheduleJobTriggerRecordRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleJobTriggerRecord>> List<ScheduleJobTriggerRecord> selectList(
        @Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleJobTriggerRecord>> List<ScheduleJobTriggerRecord> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull ScheduleJobTriggerRecord entity, @Nonnull P po) {
        ScheduleJobTriggerRecordStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull ScheduleJobTriggerRecord entity, @Nonnull P po) {
        ScheduleJobTriggerRecordStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
