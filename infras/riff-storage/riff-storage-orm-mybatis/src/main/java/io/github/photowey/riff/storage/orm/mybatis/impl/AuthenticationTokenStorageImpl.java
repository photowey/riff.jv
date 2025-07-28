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

import io.github.photowey.riff.core.domain.entity.AuthenticationToken;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.assembler.EntityAssembler;
import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.infras.model.result.meta.Meta;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractEntityExt;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.AuthenticationTokenPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.repository.AuthenticationTokenRepository;
import io.github.photowey.riff.storage.api.AuthenticationTokenStorage;
import io.github.photowey.riff.storage.orm.mybatis.assembler.AuthenticationTokenAssembler;

/**
 * {@code AuthenticationTokenStorageImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/25
 */
@Component
public class AuthenticationTokenStorageImpl
    implements AuthenticationTokenStorage<AuthenticationTokenPO>, PaginationMetaStorage<AuthenticationTokenPO> {

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    private AuthenticationTokenAssembler authenticationTokenAssembler;

    // ----------------------------------------------------------------

    @Override
    public EntityAssembler<AuthenticationToken, AuthenticationTokenPO> entityAssembler() {
        return this.authenticationTokenAssembler;
    }

    // ----------------------------------------------------------------

    @Override
    public void save(@Nonnull AuthenticationToken entity) {
        AuthenticationTokenPO po = this.toPo(entity);
        this.authenticationTokenRepository.insert(po);

        this.copyBase(entity, po);
    }

    @Override
    public void batchSave(@Nonnull Collection<AuthenticationToken> entities) {
        if (Collections.isEmpty(entities)) {
            return;
        }

        List<AuthenticationToken> images = new ArrayList<>(entities);
        List<AuthenticationTokenPO> pos = this.toPos(images);
        this.authenticationTokenRepository.batchInserts(pos, AuthenticationTokenPO.class);

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
    public void delete(@Nonnull AuthenticationToken entity) {
        if (Objects.isNull(entity.id())) {
            throw new NullPointerException("orm: the entity id can't be NULL");
        }

        this.deleteById(entity.id());
    }

    @Override
    public void deleteById(@Nonnull Long id) {
        this.authenticationTokenRepository.deleteById(id);
    }

    @Override
    public void batchDelete(@Nonnull Collection<Long> ids) {
        this.authenticationTokenRepository.deleteByIds(ids);
    }

    // ----------------------------------------------------------------

    @Override
    public void updateById(@Nonnull AuthenticationToken entity) {
        this.authenticationTokenRepository.updateById(this.toPo(entity));
    }

    // ----------------------------------------------------------------

    @Override
    public Optional<AuthenticationToken> tryFindByPrincipalId(@Nonnull Long principalId) {
        AuthenticationTokenPO tokenPo =
            this.authenticationTokenRepository.selectOne(new LambdaQueryWrapper<AuthenticationTokenPO>()
                .select(AuthenticationTokenPO::getId, AuthenticationTokenPO::getPrincipalId)
                .eq(AuthenticationTokenPO::getPrincipalId, principalId)
            );

        return Optional.ofNullable(this.toEntity(tokenPo));
    }

    @Override
    public Optional<AuthenticationToken> tryFindByUsername(@Nonnull String username) {
        AuthenticationTokenPO tokenPo = this.authenticationTokenRepository.tryFindByUsername(username);
        return Optional.ofNullable(this.toEntity(tokenPo));
    }

    @Override
    public AuthenticationToken selectOne(@Nonnull Long id) {
        return this.toEntity(this.authenticationTokenRepository.selectById(id));
    }

    @Nonnull
    @Override
    public <Q extends AbstractQuery<AuthenticationToken>> List<AuthenticationToken> selectList(@Nonnull Q query) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    @Nonnull
    @Override
    public <Q extends AbstractPaginationQuery<AuthenticationToken>> List<AuthenticationToken> selectPage(
        @Nonnull Q query,
        Consumer<Meta> fx) {
        throw new UnsupportedOperationException("Unsupported now.");
    }

    // ----------------------------------------------------------------

    @Override
    public <P extends Entity> void copyTenant(@Nonnull AuthenticationToken entity, @Nonnull P po) {
        AuthenticationTokenStorage.super.copyTenant(entity, po);

        if (po instanceof AbstractTenantEntity att) {
            entity.setTenant(att.tenant());
            entity.setPlatform(att.platform());
            entity.setApp(att.app());
        }
    }

    @Override
    public <P extends Entity> void copyExt(@Nonnull AuthenticationToken entity, @Nonnull P po) {
        AuthenticationTokenStorage.super.copyExt(entity, po);

        if (po instanceof AbstractEntityExt att) {
            entity.setVersion(att.version());
            entity.setDeleted(att.deleted());
        }
    }
}
