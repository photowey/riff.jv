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
package io.github.photowey.riff.business.uaa.core.domain.payload;

import java.io.Serial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.enums.AuthenticationDictionary;
import io.github.photowey.riff.infras.authentication.core.username.Username;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.model.empty.EmptyModel;
import io.github.photowey.riff.infras.model.payload.AbstractPayload;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {@code LoginPayload}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginPayload extends AbstractPayload<EmptyModel> {

    @Serial
    private static final long serialVersionUID = 5213277065121774652L;

    @NotBlank(message = "The username can't be blank.")
    @Schema(
        description = "Username",
        example = "admin0001",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Pattern(regexp = "^[a-zA-Z0-9]{8,32}$", message = "Username must be 8 to 32 alphanumeric characters")
    private String username;

    @NotBlank(message = "The password can't be blank.")
    @Schema(
        description = "Password",
        example = "admin@123!@#",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,32}$",
        message = "Password must be 8 to 32 characters and contain uppercase, lowercase, number and one of: !@#$%^&*"
    )
    private String password;

    @Schema(
        description = "RememberMe: 0 | 1, Only: web",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "1",
        allowableValues = {"0", "1"}
    )
    private Integer rememberMe;

    // ----------------------------------------------------------------

    @Schema(
        description = "Captcha ID, if enabled.",
        example = "70202507155719188245190111831234",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Pattern(regexp = "^[a-zA-Z0-9]{32}$", message = "Captcha ID must be 32 alphanumeric characters")
    private String captchaId;
    @Schema(
        description = "Captcha, if enabled.",
        example = "Az88",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Pattern(regexp = "^[a-zA-Z0-9]{4}$", message = "Captcha must be 4 alphanumeric characters")
    private String captcha;

    // ----------------------------------------------------------------

    /**
     * @see AuthenticationDictionary.User.Type
     */
    @JsonIgnore
    @Schema(hidden = true)
    private Integer type;

    // ----------------------------------------------------------------

    @JsonIgnore
    @Schema(hidden = true)
    private String tenant;
    @JsonIgnore
    @Schema(hidden = true)
    private String platform;
    @JsonIgnore
    @Schema(hidden = true)
    private String app;
    @JsonIgnore
    @Schema(hidden = true)
    private String client;
    @Schema(hidden = true)
    private String protocol;

    // ----------------------------------------------------------------

    @Schema(hidden = true)
    private Long userId;

    @Schema(hidden = true)
    private LoginUser loginUser;

    // ----------------------------------------------------------------

    @Override
    public void initAction() {
        this.initRememberMe();
    }

    // ----------------------------------------------------------------

    private void initRememberMe() {
        if (Objects.isNull(this.rememberMe)) {
            this.rememberMe = 0;
        }
    }

    // ----------------------------------------------------------------

    public Username toUsername(String platform, String proxy) {
        return Username.builder()
            .tenant(this.tenant())
            .platform(platform)
            .app(this.app())
            .client(this.client())
            .type(this.type())
            .username(proxy)
            .rememberMe(this.rememberMe())
            .build();
    }

    // ----------------------------------------------------------------

    public boolean determineIsRememberMe() {
        return Objects.isNotNull(this.rememberMe) && this.rememberMe == 1;
    }

    public String determineAuthenticationMode() {
        if (AuthenticationDictionary.User.Type.OAUTH_CLIENT.value() == this.type) {
            return AuthorityConstants.OAUTH_CLIENT_MODE_AUTH_SCOPE;
        }

        return AuthorityConstants.PASSWORD_MODE_AUTH_SCOPE;
    }

    public String determineTokenType() {
        if (AuthenticationDictionary.User.Type.OAUTH_CLIENT.value() == this.type) {
            return AuthorityConstants.TOKEN_TYPE_OAUTH;
        }

        return AuthorityConstants.TOKEN_TYPE_BEARER;
    }

    public int determineTokenTypeInt() {
        if (AuthenticationDictionary.User.Type.OAUTH_CLIENT.value() == this.type) {
            return AuthorityConstants.INT_TOKEN_TYPE_OAUTH;
        }

        return AuthorityConstants.INT_TOKEN_TYPE_BEARER;
    }

    // ----------------------------------------------------------------

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public Integer rememberMe() {
        return rememberMe;
    }

    public String captchaId() {
        return captchaId;
    }

    public String captcha() {
        return captcha;
    }

    public Integer type() {
        return type;
    }

    public String tenant() {
        return tenant;
    }

    public String platform() {
        return platform;
    }

    public String app() {
        return app;
    }

    public String client() {
        return client;
    }

    public String protocol() {
        return protocol;
    }

    public Long userId() {
        return userId;
    }

    public LoginUser loginUser() {
        return loginUser;
    }
}
