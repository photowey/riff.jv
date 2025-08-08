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
package io.github.photowey.riff.middleware.database.core.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractDTO}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = 7571883569200963398L;

    protected Long id;

    protected Long createBy;
    protected LocalDateTime createTime;

    // ----------------------------------------------------------------

    public Long id() {
        return id;
    }

    public Long createBy() {
        return createBy;
    }

    public LocalDateTime createTime() {
        return createTime;
    }
}
