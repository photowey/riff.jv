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
import java.util.Optional;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleJobRepository;
import io.github.photowey.riff.storage.api.ScheduleJobStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleJobAssembler;

/**
 * {@code ScheduleJobStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class ScheduleJobStorageImpl implements ScheduleJobStorage<ScheduleJobPO>, PaginationMetaStorage<ScheduleJobPO> {

    @Autowired
    private ScheduleJobRepository scheduleJobRepository;

    @Autowired
    private ScheduleJobAssembler scheduleJobAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleJob, ScheduleJobPO> entityAssembler() {
        return this.scheduleJobAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public Optional<ScheduleJob> testJobExists(ScheduleJob job) {
        ScheduleJobPO image = this.scheduleJobRepository.selectOne(new LambdaQueryWrapper<ScheduleJobPO>()
            .select(ScheduleJobPO::getId)
            .eq(ScheduleJobPO::getAppId, job.appId())
            .eq(ScheduleJobPO::getJobCode, job.jobCode())
        );

        return Optional.ofNullable(this.toEntity(image));
    }

    @Override
    public boolean testMethodNameExists(ScheduleJob job) {
        if (Strings.isEmpty(job.declaredClass()) || Strings.isEmpty(job.method())) {
            return false;
        }

        Long count = this.scheduleJobRepository.selectCount(new LambdaQueryWrapper<ScheduleJobPO>()
            .eq(ScheduleJobPO::getAppId, job.appId())
            .eq(ScheduleJobPO::getJobCode, job.jobCode())
            .eq(ScheduleJobPO::getDeclaredClass, job.declaredClass())
            .eq(ScheduleJobPO::getMethod, job.method())
        );

        return Objects.isNotNull(count) && count > 1;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean exists(@Nonnull Long id) {
        return this.scheduleJobRepository.exists(new LambdaQueryWrapper<ScheduleJobPO>().eq(ScheduleJobPO::getId, id));
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleJob entity) {
        ScheduleJobPO po = this.toPo(entity);
        this.scheduleJobRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleJob> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<ScheduleJob> images = new ArrayList<>(entities);
        List<ScheduleJobPO> pos = this.toPos(images);
        this.scheduleJobRepository.batchInserts(pos, ScheduleJobPO.class);

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
    public void delete(@Nonnull ScheduleJob entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id is required.");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.scheduleJobRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.scheduleJobRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleJob entity) {
        this.scheduleJobRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleJob selectOne(@Nonnull Long id) {
        return this.toEntity(this.scheduleJobRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleJob>> List<ScheduleJob> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleJob>> List<ScheduleJob> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull ScheduleJob entity, @Nonnull P po) {
        ScheduleJobStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull ScheduleJob entity, @Nonnull P po) {
        ScheduleJobStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
