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
package io.github.photowey.riff.infras.authentication.core.threadlocal;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.authentication.core.util.Securitys;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;

/**
 * {@code LoginUserHolder}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public final class LoginUserHolder {

    private static final ThreadLocal<LoginUser> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    private LoginUserHolder() {
        AssertionErrors.throwz(LoginUserHolder.class);
    }

    public static void set(LoginUser loginUser) {
        LOGIN_USER_THREAD_LOCAL.set(loginUser);
    }

    public static LoginUser get() {
        LoginUser loginUser = LOGIN_USER_THREAD_LOCAL.get();
        if (Objects.nonNull(loginUser)) {
            return loginUser;
        }

        return Securitys.tryAcquireLoginUser();
    }

    public static LoginUser getOr() {
        LoginUser loginUser = LOGIN_USER_THREAD_LOCAL.get();

        return Objects.isNotNull(loginUser) ? loginUser : LoginUser.newDummyLoginUser();
    }

    public static LoginUser mustGet() {
        LoginUser loginUser = get();
        if (Objects.isNull(loginUser)) {
            throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
        }

        return loginUser;
    }

    public static LoginUser asyncMustGet() {
        LoginUser loginUser = LOGIN_USER_THREAD_LOCAL.get();
        if (Objects.isNull(loginUser)) {
            throw new SecurityAuthenticationException(ExceptionStatus.UNAUTHORIZED);
        }

        return loginUser;
    }

    public static void clean() {
        LOGIN_USER_THREAD_LOCAL.remove();
    }

    // ----------------------------------------------------------------

    private static LoginUser populateDummyLoginUser(Long userId) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(Objects.defaultIfNull(userId, 0L));

        return loginUser;
    }

    // ----------------------------------------------------------------

    public static LoginUser mock() {
        return mock(0L);
    }

    public static LoginUser mock(Long userId) {
        LoginUser loginUser = populateDummyLoginUser(userId);
        LoginUserHolder.set(loginUser);

        return loginUser;
    }

    // ----------------------------------------------------------------

    public static void mock(Long userId, Consumer<LoginUser> fx) {
        mock(userId, fx, true);
    }

    public static void mock(Long userId, Runnable task) {
        mock(userId, task, true);
    }

    public static void mock(Long userId, Consumer<LoginUser> fx, boolean cleaned) {
        try {
            LoginUser loginUser = mock(userId);
            fx.accept(loginUser);
        } finally {
            if (cleaned) {
                clean();
            }
        }
    }

    public static void mock(Long userId, Runnable task, boolean cleaned) {
        try {
            mock(userId);
            task.run();
        } finally {
            if (cleaned) {
                clean();
            }
        }
    }

    // ----------------------------------------------------------------

    public static void run(Long userId, Consumer<LoginUser> fx) {
        mock(userId, fx);
    }

    public static void run(Long userId, Runnable task) {
        mock(userId, task);
    }

    public static void run(Long userId, Consumer<LoginUser> fx, boolean cleaned) {
        mock(userId, fx, cleaned);
    }

    public static void run(Long userId, Runnable task, boolean cleaned) {
        mock(userId, task, cleaned);
    }

    // ----------------------------------------------------------------

    public static void run(Consumer<LoginUser> fx) {
        run(fx, true);
    }

    public static void run(Consumer<LoginUser> fx, boolean cleaned) {
        try {
            LoginUser loginUser = get();
            fx.accept(loginUser);
        } finally {
            if (cleaned) {
                clean();
            }
        }
    }

    public static void run(LoginUser loginUser, Runnable task) {
        run(() -> loginUser, task);
    }

    public static void run(LoginUser loginUser, Runnable task, boolean cleaned) {
        run(() -> loginUser, task, cleaned);
    }

    public static void run(Supplier<LoginUser> fx, Runnable task) {
        run(fx, task, true);
    }

    public static void run(Supplier<LoginUser> fx, Runnable task, boolean cleaned) {
        try {
            set(fx.get());
            task.run();
        } finally {
            if (cleaned) {
                clean();
            }
        }
    }

    public static void runOr(Consumer<LoginUser> fx) {
        runOr(fx, true);
    }

    public static void runOr(Consumer<LoginUser> fx, boolean cleaned) {
        try {
            LoginUser loginUser = getOr();
            fx.accept(loginUser);
        } finally {
            if (cleaned) {
                clean();
            }
        }
    }

    // ----------------------------------------------------------------

    public static void unsafeRun(Consumer<LoginUser> fx) {
        LoginUser loginUser = get();
        fx.accept(loginUser);
    }

    public static void unsafeRunOr(Consumer<LoginUser> fx) {
        LoginUser loginUser = getOr();
        fx.accept(loginUser);
    }
}
