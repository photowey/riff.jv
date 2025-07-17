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
package io.github.photowey.riff.infras.crypto.core.runner;

import java.util.concurrent.Callable;

import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.crypto.core.exception.CryptoException;

/**
 * {@code SneakyRunners}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
public final class SneakyRunners {

    private SneakyRunners() {
        AssertionErrors.throwz(SneakyRunners.class);
    }

    public static void tryRun(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static void tryRun(Runnable task, String message, Object... args) {
        try {
            task.run();
        } catch (Exception e) {
            throw new CryptoException(StringFormatter.format(message, args), e);
        }
    }

    public static <T> T tryCall(Callable<T> task) {
        try {
            return task.call();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static <T> T tryCall(Callable<T> task, String message, Object... args) {
        try {
            return task.call();
        } catch (Exception e) {
            throw new CryptoException(StringFormatter.format(message, args), e);
        }
    }
}
