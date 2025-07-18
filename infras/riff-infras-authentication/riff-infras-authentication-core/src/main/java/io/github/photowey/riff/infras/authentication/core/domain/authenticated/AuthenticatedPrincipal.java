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
package io.github.photowey.riff.infras.authentication.core.domain.authenticated;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.github.photowey.riff.infras.common.serializer.jackson.timestamp.LocalDateTimeTimestampDeserializer;
import io.github.photowey.riff.infras.common.serializer.jackson.timestamp.LocalDateTimeTimestampSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code AuthenticatedPrincipal}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedPrincipal implements Serializable {

    @Serial
    private static final long serialVersionUID = 158172152192799193L;

    private String tenant;
    private String platform;
    private String app;
    private String client;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;
    private String fullname;
    private String mobile;

    private String token;

    private Integer type;
    private Integer status;
    private Integer authenticationStatus;
    private Integer deleted;

    // ----------------------------------------------------------------

    private Boolean rememberMe;

    @JsonSerialize(using = LocalDateTimeTimestampSerializer.class)
    @JsonDeserialize(using = LocalDateTimeTimestampDeserializer.class)
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------

    private Set<String> authorities = new HashSet<>();
    private Set<String> scopes = new HashSet<>();
    private Set<String> roles = new HashSet<>();

    // ----------------------------------------------------------------

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

    public Long userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String fullname() {
        return fullname;
    }

    public String mobile() {
        return mobile;
    }

    public String token() {
        return token;
    }

    public Integer type() {
        return type;
    }

    public Integer status() {
        return status;
    }

    public Integer authenticationStatus() {
        return authenticationStatus;
    }

    public Integer deleted() {
        return deleted;
    }

    public Boolean rememberMe() {
        return rememberMe;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public Set<String> authorities() {
        return authorities;
    }

    public Set<String> scopes() {
        return scopes;
    }

    public Set<String> roles() {
        return roles;
    }
}
