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
package io.github.photowey.riff.middleware.database.core.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import io.github.photowey.riff.middleware.database.core.domain.table.TableId;

/**
 * {@code Entity}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface Entity extends TableId {

    default Long id() {
        return this.getId();
    }

    default String stringId() {
        if (Objects.isNull(this.id())) {
            return null;
        }

        return String.valueOf(this.id());
    }

    // ----------------------------------------------------------------

    /**
     * The create-by.
     * |- the default is {@code LoginUser#userId}.
     *
     * @param createBy the create-by.
     */
    default void setCreateBy(Long createBy) {

    }

    default void setUpdateBy(Long updateBy) {

    }

    default void setCreateTime(LocalDateTime createTime) {

    }

    default void setUpdateTime(LocalDateTime updateTime) {

    }

    // ----------------------------------------------------------------

    default Long getCreateBy() {
        return null;
    }

    default Long getUpdateBy() {
        return null;
    }

    default LocalDateTime getCreateTime() {
        return null;
    }

    default LocalDateTime getUpdateTime() {
        return null;
    }

    // ----------------------------------------------------------------

    default void setVersion(Integer version) {

    }

    /**
     * The logic-delete.
     * |- 0: normal
     * |- 1: deleted
     */
    default void setDeleted(Integer deleted) {

    }

    // ----------------------------------------------------------------

    default Integer getVersion() {
        return 1;
    }

    default Integer getDeleted() {
        return 0;
    }
}
