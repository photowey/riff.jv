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

import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobChainPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleJobChainRepository;
import io.github.photowey.riff.storage.api.ScheduleJobChainStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleJobChainAssembler;

/**
 * {@code ScheduleJobChainStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class ScheduleJobChainStorageImpl
    implements ScheduleJobChainStorage<ScheduleJobChainPO>, PaginationMetaStorage<ScheduleJobChainPO> {

    @Autowired
    private ScheduleJobChainRepository scheduleJobChainRepository;

    @Autowired
    private ScheduleJobChainAssembler scheduleJobChainAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleJobChain, ScheduleJobChainPO> entityAssembler() {
        return this.scheduleJobChainAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleJobChain entity) {
        ScheduleJobChainPO po = this.toPo(entity);
        this.scheduleJobChainRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleJobChain> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<ScheduleJobChain> images = new ArrayList<>(entities);
        List<ScheduleJobChainPO> pos = this.toPos(images);
        this.scheduleJobChainRepository.batchInserts(pos, ScheduleJobChainPO.class);

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
    public void delete(@Nonnull ScheduleJobChain entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id is required.");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.scheduleJobChainRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.scheduleJobChainRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleJobChain entity) {
        this.scheduleJobChainRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public int refreshChildIdByCode(ScheduleJob job) {
        return this.scheduleJobChainRepository.refreshChildIdByCode(job.id(), job.jobCode());
    }

    @Override
    public int refreshChildCodeById(ScheduleJob job) {
        return this.scheduleJobChainRepository.refreshChildCodeById(job.id(), job.jobCode());
    }

    // ----------------------------------------------------------------

    @Override
    public List<Long> cycleDetect(Long childId) {
        return this.scheduleJobChainRepository.cycleDetect(childId);
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleJobChain selectOne(@Nonnull Long id) {
        return this.toEntity(this.scheduleJobChainRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleJobChain>> List<ScheduleJobChain> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleJobChain>> List<ScheduleJobChain> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull ScheduleJobChain entity, @Nonnull P po) {
        ScheduleJobChainStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull ScheduleJobChain entity, @Nonnull P po) {
        ScheduleJobChainStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
