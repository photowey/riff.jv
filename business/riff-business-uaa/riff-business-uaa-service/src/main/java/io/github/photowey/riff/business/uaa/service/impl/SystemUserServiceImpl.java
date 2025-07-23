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

import io.github.photowey.riff.business.uaa.service.SystemUserService;
import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;
import io.github.photowey.riff.infras.authentication.core.username.Username;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.storage.api.SystemUserStorage;

/**
 * {@code SystemUserServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserStorage<SystemUserPO> systemUserStorage;

    @Override
    public AuthenticationPrincipal loadPrincipal(Username proxy) {
        Optional<SystemUser> systemUserOpt = this.systemUserStorage.tryFindSystemUser(proxy.username());

        if (systemUserOpt.isPresent()) {

            SystemUser systemUser = systemUserOpt.get();

            return AuthenticationPrincipal.builder()
                .tenant(systemUser.tenant())
                .platform(systemUser.platform())
                .app(systemUser.app())
                .client(proxy.client())
                // ----------------------------------------------------------------
                .userId(systemUser.id())
                .username(systemUser.username())
                .password(systemUser.password())
                .mobile(systemUser.mobile())
                .type(proxy.type())
                // ----------------------------------------------------------------
                .status(systemUser.status())
                .authenticationStatus(systemUser.authenticationStatus())
                .deleted(systemUser.deleted())
                .build();
        }


        return null;
    }
}
