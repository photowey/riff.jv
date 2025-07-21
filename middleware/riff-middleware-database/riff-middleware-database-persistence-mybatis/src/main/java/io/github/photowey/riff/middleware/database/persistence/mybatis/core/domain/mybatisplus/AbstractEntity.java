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
package io.github.photowey.riff.middleware.database.persistence.mybatis.core.domain.mybatisplus;

import java.io.Serial;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractEntity}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Entity {

    @Serial
    private static final long serialVersionUID = -57105932654898440L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;

    @TableField(fill = FieldFill.INSERT)
    protected Long createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    // ----------------------------------------------------------------

    public Long createBy() {
        return createBy;
    }

    public Long updateBy() {
        return updateBy;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public LocalDateTime updateTime() {
        return updateTime;
    }
}
