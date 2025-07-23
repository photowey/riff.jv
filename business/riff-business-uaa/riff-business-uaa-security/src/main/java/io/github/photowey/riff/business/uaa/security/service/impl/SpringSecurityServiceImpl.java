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
package io.github.photowey.riff.business.uaa.security.service.impl;

import io.github.photowey.riff.business.uaa.security.service.SpringSecurityService;

/**
 * {@code SpringSecurityServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public class SpringSecurityServiceImpl implements SpringSecurityService {

    @Override
    public boolean hasAccount(String account) {
        return false;
    }

    @Override
    public boolean hasAccounts(String... accounts) {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        return false;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return false;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean hasScope(String scope) {
        return false;
    }

    @Override
    public boolean hasScopes(String... scopes) {
        return false;
    }

    @Override
    public boolean hasAnyScopes(String... scopes) {
        return false;
    }

    @Override
    public boolean hasNotScope(String scope) {
        return false;
    }

    @Override
    public boolean hasNotAnyScopes(String... scopes) {
        return false;
    }
}
