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
package io.github.photowey.riff.middleware.database.persistence.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import io.github.photowey.riff.middleware.database.orm.config.DatabaseOrmAutoConfiguration;
import io.github.photowey.riff.middleware.database.persistence.mybatis.meta.TenantAutoMetaObjectHandler;

/**
 * {@code DatabasePersistenceMybatisAutoConfiguration}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@AutoConfiguration(after = DatabaseOrmAutoConfiguration.class)
@Import(value = {
    DatabasePersistenceMybatisAutoConfiguration.DatabasePersistenceMybatisConfiguration.class,
})
public class DatabasePersistenceMybatisAutoConfiguration {

    @Configuration
    @MapperScan(value = {
        "io.github.photowey.riff.middleware.database.persistence.mybatis.repository"
    })
    public static class DatabasePersistenceMybatisConfiguration {

    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(SqlSessionTemplate.class)
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(this.paginationInnerInterceptor());
        interceptor.addInnerInterceptor(this.optimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(this.blockAttackInnerInterceptor());

        return interceptor;
    }

    // ----------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public MetaObjectHandler metaObjectHandler() {
        return new TenantAutoMetaObjectHandler();
    }

    // ----------------------------------------------------------------

    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(-1L);

        return paginationInnerInterceptor;
    }

    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }
}
