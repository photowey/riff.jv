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
package io.github.photowey.riff.middleware.database.persistence.mybatis.wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.springframework.util.ReflectionUtils;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.WrapperKeyword;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * {@code AppendableLambdaQueryWrapper}.
 *
 * @param <T> the {@code Database} entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public class AppendableLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> implements AutoCloseable {

    private SharedString sqlSelect = new SharedString();
    private final List<SFunction<T, ?>> cacheSelected = new ArrayList<>();

    // ----------------------------------------------------------------

    public AppendableLambdaQueryWrapper() {
        super();
    }

    public AppendableLambdaQueryWrapper(T entity) {
        super(entity);
    }

    public AppendableLambdaQueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    public AppendableLambdaQueryWrapper(
        T entity, Class<T> entityClass, SharedString sqlSelect, AtomicInteger paramNameSeq,
        Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments,
        SharedString paramAlias,
        SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);

        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.sqlSelect = sqlSelect;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;

        tryInjectParentField();
    }

    private void tryInjectParentField() {
        try {
            Field field = LambdaQueryWrapper.class.getDeclaredField("sqlSelect");
            ReflectionUtils.makeAccessible(field);
            field.set(this, this.sqlSelect);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    @Override
    public void close() throws Exception {
        this.clean();
    }

    // ----------------------------------------------------------------

    public void clean() {
        this.cacheSelected.clear();
    }

    public AppendableLambdaQueryWrapper<T> chainClean() {
        this.cacheSelected.clear();

        return this;
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public AppendableLambdaQueryWrapper<T> appendSelect(SFunction<T, ?>... columns) {
        return this.appendSelect(CollectionUtils.toList(columns));
    }

    @SuppressWarnings("unchecked")
    public AppendableLambdaQueryWrapper<T> appendSelect(
        boolean cleanSelected, SFunction<T, ?>... columns) {
        return this.appendSelect(cleanSelected, CollectionUtils.toList(columns));
    }

    public AppendableLambdaQueryWrapper<T> appendSelect(List<SFunction<T, ?>> columns) {
        return this.appendSelect(false, columns);
    }

    public AppendableLambdaQueryWrapper<T> appendSelect(
        boolean cleanSelected, List<SFunction<T, ?>> columns) {
        if (cleanSelected) {
            this.clean();
        }

        if (CollectionUtils.isNotEmpty(columns)) {
            columns.forEach(column -> {
                if (!this.cacheSelected.contains(column)) {
                    this.cacheSelected.add(column);
                }
            });
        }

        super.select(this.cacheSelected);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> eq(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.eq(condition, column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> ne(SFunction<T, ?> column, Object val) {
        super.ne(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> ne(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.ne(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> le(SFunction<T, ?> column, Object val) {
        super.le(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> le(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.le(condition, column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> lt(SFunction<T, ?> column, Object val) {
        super.lt(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> lt(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.lt(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> ge(SFunction<T, ?> column, Object val) {
        super.ge(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> ge(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.ge(condition, column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> gt(SFunction<T, ?> column, Object val) {
        super.gt(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> gt(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.gt(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> like(SFunction<T, ?> column, Object val) {
        super.like(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> like(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.like(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> notLike(SFunction<T, ?> column, Object val) {
        super.notLike(column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> notLike(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.notLike(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> likeLeft(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.likeLeft(condition, column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> likeRight(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.likeRight(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> notLikeLeft(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.notLikeLeft(condition, column, val);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> notLikeRight(
        boolean condition, SFunction<T, ?> column, Object val) {
        super.notLikeRight(condition, column, val);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> between(
        SFunction<T, ?> column, Object val1,
        Object val2) {
        super.between(column, val1, val2);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> between(
        boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        super.between(condition, column, val1, val2);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> and(boolean condition) {
        super.and(condition);

        return this;
    }

    public AppendableLambdaQueryWrapper<T> tryAnd(
        boolean condition,
        Consumer<AppendableLambdaQueryWrapper<T>> consumer) {
        return and(condition).tryAddNestedCondition(condition, consumer);
    }

    // ----------------------------------------------------------------

    public AppendableLambdaQueryWrapper<T> tryOr(boolean condition) {
        super.or(condition);

        return this;
    }

    public AppendableLambdaQueryWrapper<T> tryOr(
        boolean condition,
        Consumer<AppendableLambdaQueryWrapper<T>> consumer) {
        return tryOr(condition).tryAddNestedCondition(condition, consumer);
    }

    // ----------------------------------------------------------------

    public AppendableLambdaQueryWrapper<T> tryAddNestedCondition(
        boolean condition,
        Consumer<AppendableLambdaQueryWrapper<T>> consumer) {
        maybeDo(condition, () -> {
            final AppendableLambdaQueryWrapper<T> instance = instance();
            consumer.accept(instance);
            appendSqlSegments(WrapperKeyword.APPLY, instance);
        });

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> instance() {
        return new AppendableLambdaQueryWrapper<>(
            getEntity(), getEntityClass(), null,
            paramNameSeq, paramNameValuePairs,
            new MergeSegments(), paramAlias, SharedString.emptyString(),
            SharedString.emptyString(), SharedString.emptyString()
        );
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> orderByAsc(SFunction<T, ?> column) {
        super.orderByDesc(column);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> orderByAsc(List<SFunction<T, ?>> columns) {
        super.orderByDesc(columns);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> orderByDesc(SFunction<T, ?> column) {
        super.orderByDesc(column);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> orderByDesc(List<SFunction<T, ?>> columns) {
        super.orderByDesc(columns);

        return this;
    }

    @SafeVarargs
    public final AppendableLambdaQueryWrapper<T> tryOrderByAsc(
        SFunction<T, ?> column,
        SFunction<T, ?>... columns) {
        super.orderByAsc(column, columns);

        return this;
    }

    @SafeVarargs
    public final AppendableLambdaQueryWrapper<T> tryOrderByDesc(
        SFunction<T, ?> column,
        SFunction<T, ?>... columns) {
        super.orderByDesc(column, columns);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> groupBy(SFunction<T, ?> column) {
        super.groupBy(column);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> groupBy(boolean condition, SFunction<T, ?> column) {
        super.groupBy(condition, column);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> groupBy(boolean condition,
                                                   List<SFunction<T, ?>> columns) {
        super.groupBy(condition, columns);

        return this;
    }

    // ----------------------------------------------------------------

    @Override
    public AppendableLambdaQueryWrapper<T> last(String lastSql) {
        super.last(lastSql);

        return this;
    }

    @Override
    public AppendableLambdaQueryWrapper<T> last(boolean condition, String lastSql) {
        super.last(condition, lastSql);

        return this;
    }
}
