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
package io.github.photowey.riff.business.uaa.security.loader;

import io.github.photowey.riff.business.uaa.core.event.LoadAuthenticatedPrincipalEvent;
import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;
import io.github.photowey.riff.infras.authentication.core.passport.UsernamePassport;
import io.github.photowey.riff.infras.authentication.core.username.Username;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code AccountAuthenticationUserDetailsLoader}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
public class AccountAuthenticationUserDetailsLoader extends AbstractAuthenticationUserDetailsLoader {

    @Override
    public int getOrder() {
        return -1000;
    }

    // ----------------------------------------------------------------

    @Override
    public String protocol() {
        return AuthorityConstants.ACCOUNT_PROTOCOL;
    }

    @Override
    public AuthenticationPrincipal toAuthenticatedPrincipal(Username proxy) {
        LoadAuthenticatedPrincipalEvent event = new LoadAuthenticatedPrincipalEvent(proxy);
        this.publisher().publishEvent(event);

        return event.principal();
    }

    @Override
    public UsernamePassport toUsernamePassport(AuthenticationPrincipal principal) {
        this.checkAccountInvalid(principal);

        return UsernamePassport.builder()
            .tenant(principal.tenant())
            .platform(principal.platform())
            .app(principal.app())
            .client(principal.client())
            .userId(principal.userId())
            .username(principal.username())
            .mobile(principal.mobile())
            .type(principal.type())
            .build();
    }
}
