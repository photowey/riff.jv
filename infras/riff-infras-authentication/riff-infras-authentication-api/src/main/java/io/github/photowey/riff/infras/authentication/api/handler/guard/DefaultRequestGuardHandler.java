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
package io.github.photowey.riff.infras.authentication.api.handler.guard;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import io.github.photowey.riff.infras.authentication.api.determiner.IgnorePathDeterminer;
import io.github.photowey.riff.infras.authentication.api.matcher.ant.AntPathMatcherCreator;

/**
 * {@code DefaultRequestGuardHandler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public class DefaultRequestGuardHandler implements RequestGuardHandler, AntPathMatcherCreator {

    private final UrlPathHelper helper = this.createHelper();
    private final AntPathMatcher requestMatcher = this.createMatcher(true);

    private final IgnorePathDeterminer ignorePathDeterminer;

    public DefaultRequestGuardHandler(IgnorePathDeterminer ignorePathDeterminer) {
        this.ignorePathDeterminer = ignorePathDeterminer;
    }

    @Override
    public String getLookupPathForRequest(HttpServletRequest request) {
        return this.helper.getLookupPathForRequest(request);
    }

    @Override
    public boolean determineIsIgnoredRequest(HttpServletRequest request) {
        String path = this.helper.getLookupPathForRequest(request);
        return this.determineIsIgnoredPath(path);
    }

    @Override
    public boolean determineIsIgnoredPath(String path) {
        List<String> paths = this.determineIgnorePaths();

        for (String ignorePath : paths) {
            boolean matches = this.requestMatcher.match(ignorePath, path);
            if (matches) {
                return true;
            }
        }

        return false;
    }

    private List<String> determineIgnorePaths() {
        return this.ignorePathDeterminer.collectIgnorePaths();
    }
}
