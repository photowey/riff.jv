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

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.util.Requests;
import io.github.photowey.riff.infras.common.constant.CommonConstants;
import io.github.photowey.riff.infras.common.util.Objects;

/**
 * {@code RequestDeterminer}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public interface RequestDeterminer {

    default String determineRequestUserAgent() {
        HttpServletRequest request = Requests.getRequest();
        if (Objects.isNull(request)) {
            return CommonConstants.Symbol.EMPTY;
        }

        return Objects.defaultIfNull(
            this.determineRequestUserAgent(request),
            CommonConstants.Symbol.EMPTY
        );
    }

    default String determineRequestUserAgent(@Nonnull HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    default String determineTenantHeader() {
        return AuthorityConstants.DEFAULT_TENANT;
    }

    default String determinePlatformHeader() {
        return AuthorityConstants.DEFAULT_PLATFORM;
    }

    default String determineAppHeader() {
        return AuthorityConstants.DEFAULT_APP;
    }

    default String determineClientHeader() {
        return AuthorityConstants.DEFAULT_CLIENT;
    }
}
