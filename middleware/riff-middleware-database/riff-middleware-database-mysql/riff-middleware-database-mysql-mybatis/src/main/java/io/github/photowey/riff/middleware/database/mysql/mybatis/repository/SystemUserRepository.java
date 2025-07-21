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
package io.github.photowey.riff.middleware.database.mysql.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.github.photowey.riff.infras.model.query.AbstractQuery;
import io.github.photowey.riff.infras.model.query.pagination.AbstractPaginationQuery;
import io.github.photowey.riff.middleware.database.mysql.mybatis.core.domain.po.SystemUserPO;
import io.github.photowey.riff.middleware.database.mysql.mybatis.ext.BatchRepositoryExt;

/**
 * {@code SystemUserRepository}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface SystemUserRepository extends BatchRepositoryExt<SystemUserPO> {

    void physicalDelete(@Param("id") Long id);

    List<SystemUserPO> selectList(AbstractQuery<?> query);

    IPage<SystemUserPO> selectPage(IPage<SystemUserPO> page, AbstractPaginationQuery<?> query);


    // void logicalDelete(@Param("id") Long id);
}
