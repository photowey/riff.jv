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
import java.time.LocalDateTime;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AuthenticationToken}.
 * |- riff_authentication_token
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
public class AuthenticationToken extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = -2233039820293366076L;

    /**
     * UserId
     * |- authenticationType == Web
     * AppID
     * |- authenticationType == OAuthClient
     */
    private Long principalId;
    /**
     * AuthenticationType 1:Web 2:OAuthClient
     */
    private Integer authenticationType;
    /**
     * AuthenticationKey
     */
    private String authenticationKey;
    /**
     * TokenType 1:Bearer 2:OAuth
     */
    private Integer tokenType;
    /**
     * Token
     */
    private String token;
    /**
     * TokenExpiresIn
     */
    private Integer tokenExpiresIn;
    /**
     * TokenExpireTime
     */
    private LocalDateTime tokenExpireTime;
    /**
     * RefreshTokenEnabled 0:Disabled 1:Enabled
     */
    private Integer refreshTokenEnabled;
    /**
     * RefreshToken
     */
    private String refreshToken;
    /**
     * RefreshTokenExpiresIn
     */
    private Integer refreshTokenExpiresIn;
    /**
     * RefreshTokenExpireTime
     */
    private LocalDateTime refreshTokenExpireTime;

    // ----------------------------------------------------------------

    public boolean determineIsExpiresIn(long expiresInSeconds) {
        // TODO NOT implemented
        return true;
    }

    public boolean determineRefreshTokenIsExpired() {
        // TODO NOT implemented
        return true;
    }

    // ----------------------------------------------------------------

    public Long principalId() {
        return this.principalId;
    }

    public Integer authenticationType() {
        return this.authenticationType;
    }

    public String authenticationKey() {
        return this.authenticationKey;
    }

    public Integer tokenType() {
        return this.tokenType;
    }

    public String token() {
        return this.token;
    }

    public Integer tokenExpiresIn() {
        return this.tokenExpiresIn;
    }

    public LocalDateTime tokenExpireTime() {
        return this.tokenExpireTime;
    }

    public Integer refreshTokenEnabled() {
        return this.refreshTokenEnabled;
    }

    public String refreshToken() {
        return this.refreshToken;
    }

    public Integer refreshTokenExpiresIn() {
        return this.refreshTokenExpiresIn;
    }

    public LocalDateTime refreshTokenExpireTime() {
        return this.refreshTokenExpireTime;
    }

}
