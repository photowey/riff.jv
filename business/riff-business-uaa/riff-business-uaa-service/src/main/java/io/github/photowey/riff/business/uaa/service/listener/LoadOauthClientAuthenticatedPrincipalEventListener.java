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
package io.github.photowey.riff.business.uaa.service.listener;

import org.springframework.stereotype.Component;

import io.github.photowey.riff.business.uaa.core.event.LoadOauthClientAuthenticatedPrincipalEvent;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;

/**
 * {@code LoadOauthClientAuthenticatedPrincipalEventListener}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Component
public class LoadOauthClientAuthenticatedPrincipalEventListener
    extends AbstractApplicationListener<LoadOauthClientAuthenticatedPrincipalEvent> {

    @Override
    public void onApplicationEvent(LoadOauthClientAuthenticatedPrincipalEvent event) {
        this.sync(event);
    }

    private void sync(LoadOauthClientAuthenticatedPrincipalEvent event) {
        AuthenticationPrincipal principal = this.scheduleAppService.loadPrincipal(event.username());
        event.principal(principal);
    }
}
