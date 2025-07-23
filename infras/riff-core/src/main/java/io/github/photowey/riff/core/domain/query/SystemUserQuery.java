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
package io.github.photowey.riff.core.domain.query;

import java.io.Serial;

import io.github.photowey.riff.core.domain.entity.SystemUser;
import io.github.photowey.riff.infras.model.query.AbstractQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {@code SystemUserQuery}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemUserQuery extends AbstractQuery<SystemUser> {
    @Serial
    private static final long serialVersionUID = 7267028075777586392L;

    private Long id;
    private String username;
    private String mobile;
    private Integer status;
    private Integer authenticationStatus;

    // ----------------------------------------------------------------

    public Long id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String mobile() {
        return mobile;
    }

    public Integer status() {
        return status;
    }

    public Integer authenticationStatus() {
        return authenticationStatus;
    }
}
