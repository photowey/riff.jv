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
package io.github.photowey.riff.infras.authentication.core.decrator;

import java.util.Map;

import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.common.util.Maps;
import io.github.photowey.riff.infras.common.util.Objects;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * {@code InheritableTaskDecorator}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public class InheritableTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        LoginUser loginUser = LoginUserHolder.get();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Map<String, String> ctx = MDC.getCopyOfContextMap();
        return () -> {
            try {
                preRun(loginUser, requestAttributes, ctx);
                runnable.run();
            } finally {
                postRun(loginUser, requestAttributes, ctx);
            }
        };
    }

    private static void preRun(
        LoginUser loginUser, RequestAttributes requestAttributes, Map<String, String> ctx) {
        if (Objects.nonNull(loginUser)) {
            LoginUserHolder.set(loginUser);
        }
        if (Objects.nonNull(requestAttributes)) {
            RequestContextHolder.setRequestAttributes(requestAttributes);
        }
        if (Maps.isNotEmpty(ctx)) {
            MDC.setContextMap(ctx);
        }
    }

    private static void postRun(
        LoginUser loginUser, RequestAttributes requestAttributes, Map<String, String> ctx) {
        if (Objects.nonNull(loginUser)) {
            LoginUserHolder.clean();
        }
        if (Objects.nonNull(requestAttributes)) {
            RequestContextHolder.resetRequestAttributes();
        }
        if (Maps.isNotEmpty(ctx)) {
            MDC.clear();
        }
    }
}
