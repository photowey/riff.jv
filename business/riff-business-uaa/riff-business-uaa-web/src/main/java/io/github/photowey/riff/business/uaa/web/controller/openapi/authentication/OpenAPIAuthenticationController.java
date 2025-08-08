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
package io.github.photowey.riff.business.uaa.web.controller.openapi.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.photowey.riff.business.uaa.core.domain.dto.TokenDTO;
import io.github.photowey.riff.business.uaa.core.domain.payload.OauthClientLoginPayload;
import io.github.photowey.riff.business.uaa.service.LoginService;
import io.github.photowey.riff.infras.model.result.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code OpenAPIAuthenticationController}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Slf4j
@RestController
@RequestMapping("/openapi/v1/authentication")
@SuppressWarnings("all")
public class OpenAPIAuthenticationController {

    @Autowired
    private LoginService loginService;

    /**
     * POST :/login
     * |- Authentication | Login
     *
     * <p>
     * Handles user login requests. Receives client authentication credentials via the request body,
     * delegates to the login service for authentication, and returns an API result containing the authentication token.
     *
     * @param payload The request body object containing login information such as username and password.
     * @return Returns An {@link ApiResult} containing a {@link TokenDTO} on successful authentication.
     */
    @PostMapping("/login")
    public ApiResult<TokenDTO> login(@RequestBody @Validated OauthClientLoginPayload payload) {
        TokenDTO token = this.loginService.login(payload.toLoginPayload());
        return ApiResult.success(token);
    }
}
