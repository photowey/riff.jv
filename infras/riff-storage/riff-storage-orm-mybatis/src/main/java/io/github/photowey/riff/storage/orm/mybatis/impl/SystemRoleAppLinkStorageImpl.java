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

import io.github.photowey.riff.core.domain.entity.SystemRole;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemRolePO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.SystemRoleRepository;
import io.github.photowey.riff.storage.api.SystemRoleStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.SystemRoleAssembler;

/**
 * {@code SystemRoleStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class SystemRoleAppLinkStorageImpl implements SystemRoleStorage<SystemRolePO>, PaginationMetaStorage<SystemRolePO> {

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @Autowired
    private SystemRoleAssembler systemRoleAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<SystemRole, SystemRolePO> entityAssembler() {
        return this.systemRoleAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull SystemRole entity) {
        SystemRolePO po = this.toPo(entity);
        this.systemRoleRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<SystemRole> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<SystemRole> images = new ArrayList<>(entities);
        List<SystemRolePO> pos = this.toPos(images);
        this.systemRoleRepository.batchInserts(pos, SystemRolePO.class);

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
    public void delete(@Nonnull SystemRole entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id can't be NULL");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.systemRoleRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.systemRoleRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull SystemRole entity) {
        this.systemRoleRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public SystemRole selectOne(@Nonnull Long id) {
        return this.toEntity(this.systemRoleRepository.selectById(id));
    }

    @Override
    public <Q extends AbstractQuery<SystemRole>> List<SystemRole> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Override
    public <Q extends AbstractPaginationQuery<SystemRole>> List<SystemRole> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull SystemRole entity, @Nonnull P po) {
        SystemRoleStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity ptt) {
            entity.setTenant(ptt.tenant());
            entity.setPlatform(ptt.platform());
            entity.setApp(ptt.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull SystemRole entity, @Nonnull P po) {
        SystemRoleStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt ett) {
            entity.setVersion(ett.version());
            entity.setDeleted(ett.deleted());
        }
    }
}
