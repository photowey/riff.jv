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
package io.github.photowey.riff.infras.authentication.jjwt.token.context;

import java.io.Serial;
import java.util.function.Consumer;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.JwtBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code DefaultTokenContext}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class DefaultTokenContext implements TokenContext {

    @Serial
    private static final long serialVersionUID = -8844056494216358503L;

    private Authentication authentication;
    private boolean rememberMe;
    private Consumer<JwtBuilder> callback;
    private long now;
}
