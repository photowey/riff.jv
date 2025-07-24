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

import io.github.photowey.riff.core.domain.entity.SystemRoleAppLink;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemRoleAppLinkPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.SystemRoleAppLinkRepository;
import io.github.photowey.riff.storage.api.SystemRoleAppLinkStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.SystemRoleAppLinkAssembler;

/**
 * {@code SystemRoleAppLinkStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Component
public class SystemRoleAppLinkStorageImpl
    implements SystemRoleAppLinkStorage<SystemRoleAppLinkPO>, PaginationMetaStorage<SystemRoleAppLinkPO> {

    @Autowired
    private SystemRoleAppLinkRepository systemRoleAppLinkRepository;

    @Autowired
    private SystemRoleAppLinkAssembler systemRoleAppLinkAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<SystemRoleAppLink, SystemRoleAppLinkPO> entityAssembler() {
        return this.systemRoleAppLinkAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull SystemRoleAppLink entity) {
        SystemRoleAppLinkPO po = this.toPo(entity);
        this.systemRoleAppLinkRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<SystemRoleAppLink> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<SystemRoleAppLink> images = new ArrayList<>(entities);
        List<SystemRoleAppLinkPO> pos = this.toPos(images);
        this.systemRoleAppLinkRepository.batchInserts(pos, SystemRoleAppLinkPO.class);

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
    public void delete(@Nonnull SystemRoleAppLink entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id can't be NULL");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.systemRoleAppLinkRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.systemRoleAppLinkRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull SystemRoleAppLink entity) {
        this.systemRoleAppLinkRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public SystemRoleAppLink selectOne(@Nonnull Long id) {
        return this.toEntity(this.systemRoleAppLinkRepository.selectById(id));
    }

    @Override
    public <Q extends AbstractQuery<SystemRoleAppLink>> List<SystemRoleAppLink> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Override
    public <Q extends AbstractPaginationQuery<SystemRoleAppLink>> List<SystemRoleAppLink> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }
}
