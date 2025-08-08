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
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.photowey.riff.business.uaa.service.ScheduleAppAuthenticationService;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;
import io.github.photowey.riff.infras.authentication.core.enums.AuthenticationDictionary;
import io.github.photowey.riff.infras.authentication.core.username.Username;
import io.github.photowey.riff.infras.common.enums.CommonDictionary;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.storage.api.engine.StorageEngine;

/**
 * {@code ScheduleAppAuthenticationServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Service
public class ScheduleAppAuthenticationServiceImpl implements ScheduleAppAuthenticationService {

    @Autowired
    private StorageEngine storageEngine;

    @Override
    public AuthenticationPrincipal loadPrincipal(Username proxy) {
        Optional<ScheduleApp> appOpt = this.storageEngine.scheduleAppStorage().loadPrincipal(proxy.username());
        if (appOpt.isPresent()) {
            ScheduleApp app = appOpt.get();
            return this.toAuthenticationPrincipal(app);
        }

        return null;
    }

    private AuthenticationPrincipal toAuthenticationPrincipal(ScheduleApp app) {
        Set<String> emptySet = Collections.emptySet();
        return AuthenticationPrincipal.builder()
            .tenant(app.tenant())
            .platform(app.platform())
            .app(app.app())
            // ----------------------------------------------------------------
            .userId(app.id())
            .username(app.accessKey())
            .password(app.accessSecret())
            .fullname(app.appName())
            .twofaEnabled(CommonDictionary.Boolean.FALSE.value())
            // ----------------------------------------------------------------
            .type(AuthenticationDictionary.User.Type.OAUTH_CLIENT.value())
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .deleted(CommonDictionary.Boolean.FALSE.value())
            // ----------------------------------------------------------------
            .rememberMe(false)
            .createdAt(app.createTime())
            // ----------------------------------------------------------------
            .authorities(emptySet)
            .scopes(emptySet)
            .roles(emptySet)
            .build();
    }
}
