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
package io.github.photowey.riff.business.uaa.core.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code RefreshDTO}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7211636547652692349L;

    private Integer enabled;
    private String token;
    private String type;
    private Long issuedAt;
    private Long expiresIn;

    // ----------------------------------------------------------------

    public Integer enabled() {
        return enabled;
    }

    public String token() {
        return token;
    }

    public String type() {
        return type;
    }

    public Long issuedAt() {
        return issuedAt;
    }

    public Long expiresIn() {
        return expiresIn;
    }
}
