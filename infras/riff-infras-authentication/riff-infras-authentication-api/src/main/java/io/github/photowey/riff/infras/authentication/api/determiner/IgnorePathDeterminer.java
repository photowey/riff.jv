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
package io.github.photowey.riff.infras.authentication.api.determiner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.Nullable;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import io.github.photowey.riff.infras.authentication.api.enhancer.IgnorePathEnhancer;
import io.github.photowey.riff.infras.authentication.core.constant.AntPathConstants;
import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.property.getter.SecurityPropertiesGetter;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.ioc.context.getter.BeanFactoryGetter;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

/**
 * {@code IgnorePathDeterminer}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public class IgnorePathDeterminer extends AbstractBeanFactoryHolder
    implements SecurityPropertiesGetter, BeanFactoryGetter, BeanFactoryAware {

    private static final String ANY_REQUEST_ANT = AntPathConstants.ANT_PATTERN_ALL;
    private static final String ANY_REQUEST_PATH = AntPathConstants.PATH_PATTERN_ALL;
    private static final String[] IGNORED_PATHS = AuthorityConstants.DEFAULT_IGNORED_PATHS;

    private static final String[] MUST_IGNORED_PATHS = new String[] {
        "/actuator/**",
        "/healthy",
        "/error",
    };

    public Set<String> determineCustomIgnorePaths() {
        Map<String, IgnorePathEnhancer> beans =
            this.listableBeanFactory().getBeansOfType(IgnorePathEnhancer.class);

        List<IgnorePathEnhancer> ignorePathEnhancers = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(ignorePathEnhancers);
        Set<String> paths = new HashSet<>();
        for (IgnorePathEnhancer ignorePathEnhancer : ignorePathEnhancers) {
            paths.addAll(ignorePathEnhancer.ignorePaths());
        }

        return paths;
    }

    // ----------------------------------------------------------------

    public List<String> collectIgnorePaths() {
        Set<String> webappPaths = new HashSet<>(this.securityProperties().ignores().paths());
        for (String mustIgnoredPath : MUST_IGNORED_PATHS) {
            if (Collections.isNotContains(webappPaths, mustIgnoredPath)) {
                webappPaths.add(mustIgnoredPath);
            }
        }

        for (String mustIgnoredPath : IGNORED_PATHS) {
            if (Collections.isNotContains(webappPaths, mustIgnoredPath)) {
                webappPaths.add(mustIgnoredPath);
            }
        }

        Set<String> customPaths = this.determineCustomIgnorePaths();
        if (Collections.isNotEmpty(customPaths)) {
            webappPaths.addAll(customPaths);
        }

        return new ArrayList<>(webappPaths);
    }

    public Set<String> collectIgnorePaths(String prefix) {
        return this.collectIgnorePaths(path -> path.startsWith(prefix)
            ? path
            : prefix + path);
    }

    public Set<String> collectIgnorePaths(Function<String, String> fx) {
        return this.collectIgnorePaths().stream().map(fx).collect(Collectors.toSet());
    }

    // ----------------------------------------------------------------

    public String[] collectIgnorePathArray() {
        return this.collectIgnorePaths().toArray(String[]::new);
    }

    public Set<String> determineIgnorePathsByAnyRequestAnt() {
        return this.collectIgnorePaths(ANY_REQUEST_ANT);
    }

    public Set<String> determineIgnorePathsByAnyRequestPath() {
        return this.collectIgnorePaths(ANY_REQUEST_PATH);
    }

    // ----------------------------------------------------------------

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return Collections.isNotEmpty(collection);
    }
}

