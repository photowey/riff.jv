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
package io.github.photowey.riff.middleware.database.mysql.mybatis.meta;

import java.time.LocalDateTime;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;
import io.github.photowey.riff.middleware.database.meta.AutoMetaObjectHandler;
import io.github.photowey.riff.middleware.database.mysql.mybatis.core.domain.mybatisplus.AbstractTenantEntity;

/**
 * {@code TenantAutoMetaObjectHandler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public class TenantAutoMetaObjectHandler extends AutoMetaObjectHandler {

    @Override
    protected void tryInsertInject(Entity root) {
        LoginUserHolder.unsafeRunOr((loginUser) -> {
            LocalDateTime now = LocalDateTime.now();

            tryInjectCreator(root, loginUser);
            tryInjectTime(root, now);

            tryInjectVersion(root);
            tryInjectDeleted(root);

            // EXT
            tryInjectTenant(root);
        });
    }

    // ----------------------------------------------------------------

    private void tryInjectTenant(Entity root) {
        if (root instanceof AbstractTenantEntity tt) {
            if (Strings.isEmpty(tt.tenant())) {
                tt.setTenant(AuthorityConstants.DEFAULT_TENANT);
            }

            if (Strings.isEmpty(tt.platform())) {
                tt.setPlatform(AuthorityConstants.DEFAULT_PLATFORM);
            }

            if (Strings.isEmpty(tt.app())) {
                tt.setApp(AuthorityConstants.DEFAULT_APP);
            }
        }
    }
}
