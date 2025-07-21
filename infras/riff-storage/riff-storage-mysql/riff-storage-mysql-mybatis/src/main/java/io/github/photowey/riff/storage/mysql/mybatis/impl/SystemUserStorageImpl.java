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
package io.github.photowey.riff.storage.mysql.mybatis.impl;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.mysql.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.middleware.database.mysql.mybatis.repository.SystemUserRepository;
import io.github.photowey.riff.storage.api.SystemUserStorage;
import io.github.photowey.riff.storage.mysql.mybatis.assembler.SystemUserAssembler;

/**
 * {@code SystemUserStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Component
public class SystemUserStorageImpl implements SystemUserStorage<SystemUserPO>, PaginationMetaStorage<SystemUserPO> {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private SystemUserAssembler systemUserAssembler;

    @Override
    public void save(@Nonnull SystemUser entity) {
        this.systemUserRepository.insert(this.toPo(entity));
    }

    @Override
    public void batchSave(@Nonnull Collection<SystemUser> entities) {
        this.systemUserRepository.batchInserts(this.toPos(entities), SystemUserPO.class);
    }

    @Override
    public void delete(@Nonnull SystemUser entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("the entity id can't be NULL");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.systemUserRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.systemUserRepository.deleteByIds(ids);
    }

    @Override
    public void updateById(@Nonnull SystemUser entity) {
        this.systemUserRepository.updateById(this.toPo(entity));
    }

    @Override
    public SystemUser selectOne(@Nonnull Long id) {
        return this.toEntity(this.systemUserRepository.selectById(id));
    }

    @Override
    public List<SystemUser> selectList(@Nonnull AbstractQuery<SystemUser> query) {
        return this.toEntities(this.systemUserRepository.selectList(query));
    }

    @Override
    public List<SystemUser> selectPage(@Nonnull AbstractPaginationQuery<SystemUser> query, Consumer<Meta> fx) {
        IPage<SystemUserPO> page = new Page<>(query.pageNo(), query.pageSize());
        this.systemUserRepository.selectPage(page, query);

        Meta meta = this.toMeta(page);
        fx.accept(meta);

        return this.toEntities(page.getRecords());
    }

    @Override
    public SystemUserPO toPo(@Nullable SystemUser tt) {
        if (Objects.isNull(tt)) {
            return null;
        }

        return this.systemUserAssembler.toEntity(tt);
    }

    @Override
    public SystemUser toEntity(@Nullable SystemUserPO po) {
        if (Objects.isNull(po)) {
            return null;
        }

        return this.systemUserAssembler.toDto(po);
    }
}
