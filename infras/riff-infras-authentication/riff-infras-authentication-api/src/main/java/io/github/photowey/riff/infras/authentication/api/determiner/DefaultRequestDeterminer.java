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
package io.github.photowey.riff.infras.authentication.api.determiner;

import jakarta.servlet.http.HttpServletRequest;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.util.Requests;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;

/**
 * {@code DefaultRequestDeterminer}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public class DefaultRequestDeterminer implements RequestDeterminer {

    @Override
    public String determineTenantHeader() {
        HttpServletRequest request = Requests.getRequest();
        if (Objects.isNull(request)) {
            return AuthorityConstants.DEFAULT_TENANT;
        }

        String platform = request.getHeader(AuthorityConstants.TENANT_HEADER);
        if (Strings.isEmpty(platform)) {
            return AuthorityConstants.DEFAULT_TENANT;
        }

        return platform;
    }

    @Override
    public String determinePlatformHeader() {
        HttpServletRequest request = Requests.getRequest();
        if (Objects.isNull(request)) {
            return AuthorityConstants.DEFAULT_PLATFORM;
        }

        String platform = request.getHeader(AuthorityConstants.PLATFORM_HEADER);
        if (Strings.isEmpty(platform)) {
            return AuthorityConstants.DEFAULT_PLATFORM;
        }

        return platform;
    }

    @Override
    public String determineAppHeader() {
        HttpServletRequest request = Requests.getRequest();
        if (Objects.isNull(request)) {
            return AuthorityConstants.DEFAULT_APP;
        }

        String app = request.getHeader(AuthorityConstants.APP_HEADER);
        if (Strings.isEmpty(app)) {
            return AuthorityConstants.DEFAULT_APP;
        }

        return app;
    }

    @Override
    public String determineClientHeader() {
        HttpServletRequest request = Requests.getRequest();
        if (Objects.isNull(request)) {
            return AuthorityConstants.DEFAULT_CLIENT;
        }

        String client = request.getHeader(AuthorityConstants.CLIENT_HEADER);
        if (Strings.isEmpty(client)) {
            return AuthorityConstants.PASSPORT_AUTHENTICATION_CLIENT_WEB;
        }

        return client;
    }
}
