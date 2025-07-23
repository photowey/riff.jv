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
package io.github.photowey.riff.business.uaa.security.service;

/**
 * {@code SpringSecurityService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public interface SpringSecurityService extends SecurityService {

    boolean hasRole(String role);

    boolean hasAnyRoles(String... roles);

    // ----------------------------------------------------------------

    boolean hasScope(String scope);

    boolean hasScopes(String... scopes);

    boolean hasAnyScopes(String... scopes);

    boolean hasNotScope(String scope);

    boolean hasNotAnyScopes(String... scopes);
}
