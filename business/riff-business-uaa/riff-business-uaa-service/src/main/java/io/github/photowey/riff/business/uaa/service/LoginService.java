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
package io.github.photowey.riff.business.uaa.service;

import io.github.photowey.riff.business.uaa.core.domain.dto.TokenDTO;
import io.github.photowey.riff.business.uaa.core.domain.payload.LoginPayload;
import io.github.photowey.riff.infras.authentication.jjwt.engine.AuthenticationEngineGetter;
import io.github.photowey.riff.infras.authentication.property.getter.SecurityPropertiesGetter;
import io.github.photowey.riff.infras.common.nanoid.NanoId;
import io.github.photowey.riff.infras.crypto.integrated.Cryptos;

/**
 * {@code LoginService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
public interface LoginService extends AuthenticationEngineGetter, SecurityPropertiesGetter {

    /**
     * Authenticates a user or client and generates a login token.
     *
     * <p>This method supports multiple authentication channels including:
     * <ul>
     *     <li>Web login (e.g., user login via browser)</li>
     *     <li>OAuth client authentication</li>
     *     <li>Other future authentication methods</li>
     * </ul>
     *
     * @param payload the login payload containing authentication details {@link LoginPayload}
     * @return a {@link TokenDTO} containing the generated authentication token and related metadata
     */
    TokenDTO login(LoginPayload payload);

    // ----------------------------------------------------------------

    default long now() {
        return System.currentTimeMillis();
    }

    default String jwtId() {
        return NanoId.randomNumberNanoId(32);
    }

    default String audit(String platform) {
        return Cryptos.HASH.md5(platform);
    }
}
