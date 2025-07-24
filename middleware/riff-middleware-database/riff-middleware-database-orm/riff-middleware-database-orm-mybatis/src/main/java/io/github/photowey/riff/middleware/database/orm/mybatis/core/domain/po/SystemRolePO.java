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
package io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code SystemRole}.
 * |- riff_system_role
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
@TableName("riff_system_role")
public class SystemRole extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = 879565378031440385L;

    /**
     * RoleCode
     */
    private String roleCode;
    /**
     * RoleName
     */
    private String roleName;
    /**
     * PrincipalID 0:Default
     */
    private Long principalId;
    /**
     * Remark
     */
    private String remark;

    // ----------------------------------------------------------------

    public String roleCode() {
        return this.roleCode;
    }

    public String roleName() {
        return this.roleName;
    }

    public Long principalId() {
        return this.principalId;
    }

    public String remark() {
        return this.remark;
    }

}
