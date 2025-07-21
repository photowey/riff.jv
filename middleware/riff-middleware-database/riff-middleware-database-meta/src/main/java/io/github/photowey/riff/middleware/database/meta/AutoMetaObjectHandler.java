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
package io.github.photowey.riff.middleware.database.meta;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.middleware.database.core.domain.entity.Entity;

/**
 * {@code AutoMetaObjectHandler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public class AutoMetaObjectHandler implements MetaObjectHandler {

    public static final int DEFAULT_FIELD_VERSION = 0;
    public static final int LOGIC_NORMAL = 0;
    public static final int LOGIC_DELETED = 1;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (this.predicateIsDatabaseRootEntity(metaObject)) {
            this.tryInsertInject((Entity) metaObject.getOriginalObject());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (this.predicateIsDatabaseRootEntity(metaObject)) {
            this.tryUpdateInject((Entity) metaObject.getOriginalObject());
        }
    }

    // ----------------------------------------------------------------

    protected void tryInsertInject(Entity root) {
        LoginUserHolder.unsafeRunOr((loginUser) -> {
            LocalDateTime now = LocalDateTime.now();

            tryInjectCreator(root, loginUser);
            tryInjectTime(root, now);

            tryInjectVersion(root);
            tryInjectDeleted(root);
        });
    }

    protected void tryInjectVersion(Entity root) {
        if (Objects.isNull(root.getVersion())) {
            root.setVersion(DEFAULT_FIELD_VERSION);
        }
    }

    protected void tryInjectDeleted(Entity root) {
        if (Objects.isNull(root.getDeleted())) {
            root.setDeleted(LOGIC_NORMAL);
        }
    }

    protected void tryUpdateInject(Entity root) {
        LoginUserHolder.unsafeRunOr((loginUser) -> {
            LocalDateTime now = LocalDateTime.now();

            tryInjectUpdater(root, loginUser, now);
        });
    }

    // ----------------------------------------------------------------

    protected void tryInjectUpdater(Entity root, LoginUser loginUser, LocalDateTime now) {
        if (Objects.isNull(root.getUpdateTime())) {
            root.setUpdateTime(now);
        }
        if (Objects.isNull(root.getUpdateBy())) {
            root.setUpdateBy(loginUser.userId());
        }
    }

    protected void tryInjectCreator(Entity root, LoginUser loginUser) {
        if (Objects.isNull(root.getCreateBy())) {
            root.setCreateBy(loginUser.userId());
        }
        if (Objects.isNull(root.getUpdateBy())) {
            root.setUpdateBy(loginUser.userId());
        }
    }

    protected void tryInjectTime(Entity root, LocalDateTime now) {
        if (Objects.isNull(root.getCreateTime())) {
            root.setCreateTime(now);
        }
        if (Objects.isNull(root.getUpdateTime())) {
            root.setUpdateTime(now);
        }
    }

    // ----------------------------------------------------------------

    protected boolean predicateIsDatabaseRootEntity(MetaObject metaObject) {
        return (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof Entity);
    }
}

