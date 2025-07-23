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
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.SystemUserRepository;
import io.github.photowey.riff.storage.api.SystemUserStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.SystemUserAssembler;

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

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<SystemUser, SystemUserPO> entityAssembler() {
        return this.systemUserAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull SystemUser entity) {
        SystemUserPO po = this.toPo(entity);
        this.systemUserRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<SystemUser> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<SystemUser> images = new ArrayList<>(entities);
        List<SystemUserPO> pos = this.toPos(images);
        this.systemUserRepository.batchInserts(pos, SystemUserPO.class);

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
    public void delete(@Nonnull SystemUser entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id can't be NULL");
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

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull SystemUser entity) {
        this.systemUserRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public Optional<SystemUser> tryFindSystemUser(@Nonnull String username) {
        SystemUserPO selected = this.trySelectSystemUser(username);

        return Optional.ofNullable(this.toEntity(selected));
    }

    // ----------------------------------------------------------------

    @Override
    public SystemUser selectOne(@Nonnull Long id) {
        return this.toEntity(this.systemUserRepository.selectById(id));
    }

    @Override
    public <Q extends AbstractQuery<SystemUser>> List<SystemUser> selectList(@Nonnull Q query) {
        return this.toEntities(this.systemUserRepository.selectList(query));
    }

    @Override
    public <Q extends AbstractPaginationQuery<SystemUser>> List<SystemUser> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        IPage<SystemUserPO> page = this.copyPage(query);
        this.systemUserRepository.selectPage(page, query);

        Meta meta = this.toMeta(page);
        fx.accept(meta);

        return this.toEntities(page.getRecords());
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull SystemUser entity, @Nonnull P po) {
        SystemUserStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull SystemUser entity, @Nonnull P po) {
        SystemUserStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }

    // ----------------------------------------------------------------

    private SystemUserPO trySelectSystemUser(String username) {
        return this.systemUserRepository.selectOne(new LambdaQueryWrapper<SystemUserPO>()
            .select(
                SystemUserPO::getId,
                SystemUserPO::getCreateTime,
                SystemUserPO::getCreateBy,
                SystemUserPO::getDeleted,
                SystemUserPO::getTenant,
                SystemUserPO::getPlatform,
                SystemUserPO::getApp,
                SystemUserPO::getUsername,
                SystemUserPO::getPassword,
                SystemUserPO::getMobile,
                SystemUserPO::getAvatar,
                SystemUserPO::getTwofaEnabled,
                SystemUserPO::getTwofaSecret,
                SystemUserPO::getStatus,
                SystemUserPO::getAuthenticationStatus
            )
            .eq(SystemUserPO::getUsername, username)
        );
    }
}
