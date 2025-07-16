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
package io.github.photowey.riff.infras.ioc.core.enums.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code ApplicationContextHolder}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public enum ApplicationContextHolder {

    /**
     * {@link ConfigurableApplicationContext}
     */
    INSTANCE,

    ;

    /**
     * ApplicationContext.
     * {@link ConfigurableApplicationContext}.
     */
    private ConfigurableApplicationContext applicationContext;

    private final Map<Class<?>, Object> sharedObjects = new ConcurrentHashMap<>();

    // ----------------------------------------------------------------

    public void applicationContext(final ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ConfigurableApplicationContext applicationContext() {
        return this.applicationContext;
    }

    // ----------------------------------------------------------------

    public <T> T getBean(Class<T> requiredType) {
        return this.applicationContext.getBean(requiredType);
    }

    public <T> T getBean(String beanName) {
        return (T) this.applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        return this.applicationContext.getBean(beanName, requiredType);
    }

    // ----------------------------------------------------------------

    public <T> void setSharedObject(Class<T> sharedType, T t) {
        this.sharedObjects.put(sharedType, t);
    }

    public <T> T getSharedObject(Class<T> sharedType) {
        Object target = this.sharedObjects.get(sharedType);
        if (null == target) {
            target = this.applicationContext.getBean(sharedType);
        }

        return (T) target;
    }

    // ----------------------------------------------------------------

    public void cleanSharedObjects() {
        this.sharedObjects.clear();
    }

    // ----------------------------------------------------------------

    public static void clean() {
        INSTANCE.cleanSharedObjects();
    }
}



