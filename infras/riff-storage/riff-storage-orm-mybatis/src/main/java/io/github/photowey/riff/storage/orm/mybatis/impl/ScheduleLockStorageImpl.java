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

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.core.domain.entity.ScheduleLock;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleLockPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.ScheduleLockRepository;
import io.github.photowey.riff.storage.api.ScheduleLockStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.ScheduleLockAssembler;

/**
 * {@code ScheduleLockStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Component
public class ScheduleLockStorageImpl
    implements ScheduleLockStorage<ScheduleLockPO>, PaginationMetaStorage<ScheduleLockPO> {

    @Autowired
    private ScheduleLockRepository scheduleLockRepository;

    @Autowired
    private ScheduleLockAssembler scheduleLockAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<ScheduleLock, ScheduleLockPO> entityAssembler() {
        return this.scheduleLockAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull ScheduleLock entity) {
        ScheduleLockPO po = this.toPo(entity);
        this.scheduleLockRepository.insert(po);
    }

    @Override
    public void batchSave(@Nonnull Collection<ScheduleLock> entities) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    // ----------------------------------------------------------------

    @Override
    public void delete(@Nonnull ScheduleLock entity) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull ScheduleLock entity) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    // ----------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleLock tryLock(String lockKey) {
        ScheduleLockPO po = this.scheduleLockRepository.selectForUpdate(lockKey);
        return this.scheduleLockAssembler.toDto(po);
    }

    // ----------------------------------------------------------------

    @Override
    public ScheduleLock selectOne(@Nonnull Long id) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<ScheduleLock>> List<ScheduleLock> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<ScheduleLock>> List<ScheduleLock> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }
}
