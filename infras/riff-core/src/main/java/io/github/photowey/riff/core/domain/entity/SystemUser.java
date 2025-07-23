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
package io.github.photowey.riff.core.domain.entity;

import java.io.Serial;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;
import io.github.photowey.riff.middleware.database.core.domain.table.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code SystemUser}.
 * |- riff_system_user
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemUser extends AbstractTenantEntity implements TableId {

    @Serial
    private static final long serialVersionUID = 5381845847705667691L;

    /**
     * Username
     */
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * Email
     */
    private String email;
    /**
     * Mobile
     */
    private String mobile;
    /**
     * Avatar
     */
    private String avatar;
    /**
     * TwofaEnabled 0:Disabled 1:Enabled
     */
    private Integer twofaEnabled;
    /**
     * TwofaSecret
     */
    private String twofaSecret;

    /**
     * Status
     * |- 1: Unactivated 2: Activated 4: Frozen 8: Forbidden 16: Expired 32: Locked
     */
    private Integer status;
    /**
     * Authentication Status
     * |- 1: Unauthenticated 2: Authenticating 4: Authenticated 8: Authentication Failed
     */
    private Integer authenticationStatus;

    // ----------------------------------------------------------------

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public String email() {
        return this.email;
    }

    public String mobile() {
        return this.mobile;
    }

    public String avatar() {
        return this.avatar;
    }

    public Integer twofaEnabled() {
        return this.twofaEnabled;
    }

    public String twofaSecret() {
        return this.twofaSecret;
    }

    public Integer status() {
        return status;
    }

    public Integer authenticationStatus() {
        return authenticationStatus;
    }
}
