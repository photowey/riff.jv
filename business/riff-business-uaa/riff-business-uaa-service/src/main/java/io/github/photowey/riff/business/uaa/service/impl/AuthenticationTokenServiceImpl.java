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
package io.github.photowey.riff.business.uaa.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.uaa.service.AuthenticationTokenService;
import io.github.photowey.riff.core.domain.entity.AuthenticationToken;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.AuthenticationTokenPO;
import io.github.photowey.riff.storage.api.AuthenticationTokenStorage;

/**
 * {@code AuthenticationTokenServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/25
 */
@Service
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

    @Autowired
    private AuthenticationTokenStorage<AuthenticationTokenPO> authenticationTokenStorage;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asyncRefresh(AuthenticationToken token) {
        Optional<AuthenticationToken> tokenOpt = this.determineAuthenticationTokenLatest(token);
        if (tokenOpt.isPresent()) {
            this.copyBase(token, tokenOpt.get());
            this.authenticationTokenStorage.updateById(token);

            return;
        }

        this.populateCreateBase(token);
        this.authenticationTokenStorage.save(token);
    }

    @Override
    public AuthenticationToken tryReuse(String username) {
        return null;
    }

    private void populateCreateBase(AuthenticationToken token) {
        token.setCreateBy(token.principalId());
        token.setUpdateBy(token.principalId());
    }

    private void copyBase(AuthenticationToken token, AuthenticationToken lastestToken) {
        token.setId(lastestToken.id());
        token.setUpdateBy(token.principalId());
    }

    private Optional<AuthenticationToken> determineAuthenticationTokenLatest(AuthenticationToken token) {
        return this.authenticationTokenStorage.tryFindByPrincipalId(token.principalId());
    }
}
