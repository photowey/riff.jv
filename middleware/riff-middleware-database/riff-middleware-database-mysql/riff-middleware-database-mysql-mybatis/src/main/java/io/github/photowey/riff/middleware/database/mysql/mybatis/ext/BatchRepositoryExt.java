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
package io.github.photowey.riff.middleware.database.mysql.mybatis.ext;

import java.util.Collection;
import java.util.function.BiConsumer;

import jakarta.annotation.Nonnull;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/**
 * {@code RepositoryExt}.
 *
 * @param <T> The {@code Database} entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface BatchRepositoryExt<T> extends RepositoryExt<T> {

    int BATCH_SIZE_THRESHOLD = 1000;
    Log log = LogFactory.getLog(BatchRepositoryExt.class);

    default boolean batchInserts(@Nonnull Collection<T> entityList, @Nonnull Class<T> entityClass) {
        return this.executeBatch(entityList, entityClass, BATCH_SIZE_THRESHOLD, SqlMethod.INSERT_ONE);
    }

    default boolean batchUpdates(@Nonnull Collection<T> entityList, @Nonnull Class<T> entityClass) {
        return this.executeBatch(entityList, entityClass, BATCH_SIZE_THRESHOLD, SqlMethod.UPDATE_BY_ID);
    }

    default boolean executeBatch(
        @Nonnull Collection<T> entityList,
        @Nonnull Class<T> entityClass,
        int batchSize,
        @Nonnull SqlMethod sqlMethod) {
        if (entityList.size() > BATCH_SIZE_THRESHOLD) {
            throw new UnsupportedOperationException("Unsupported batch-size: " + entityList.size());
        }

        return this.executeBatch(entityList, entityClass, batchSize, (sqlSession, entity) -> {
            if (SqlMethod.INSERT_ONE.equals(sqlMethod)) {
                this.insert(entity);
            }
            if (SqlMethod.UPDATE_BY_ID.equals(sqlMethod)) {
                this.updateById(entity);
            }

            if (SqlMethod.DELETE_BY_ID.equals(sqlMethod)) {
                this.deleteById(entity);
            }
        });
    }

    default boolean executeBatch(
        @Nonnull Collection<T> entityList,
        @Nonnull Class<T> entityClass,
        int batchSize,
        @Nonnull BiConsumer<SqlSession, T> callback) {
        return SqlHelper.executeBatch(entityClass, this.log(), entityList, batchSize, callback);
    }

    default Log log() {
        return log;
    }
}
