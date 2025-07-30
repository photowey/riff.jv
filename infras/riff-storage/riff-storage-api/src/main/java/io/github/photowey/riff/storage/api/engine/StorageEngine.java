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
package io.github.photowey.riff.storage.api.engine;

import io.github.photowey.riff.infras.ioc.context.engine.Engine;
import io.github.photowey.riff.storage.api.ScheduleAppStorage;
import io.github.photowey.riff.storage.api.ScheduleClientStorage;
import io.github.photowey.riff.storage.api.ScheduleJobChainStorage;
import io.github.photowey.riff.storage.api.ScheduleJobStorage;
import io.github.photowey.riff.storage.api.ScheduleJobTriggerRecordStorage;
import io.github.photowey.riff.storage.api.ScheduleJobTriggerStatStorage;
import io.github.photowey.riff.storage.api.ScheduleLockStorage;
import io.github.photowey.riff.storage.api.SystemRoleAppLinkStorage;
import io.github.photowey.riff.storage.api.SystemRoleStorage;
import io.github.photowey.riff.storage.api.SystemUserRoleLinkStorage;
import io.github.photowey.riff.storage.api.SystemUserStorage;

/**
 * {@code StorageEngine}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/30
 */
@SuppressWarnings("unchecked")
public interface StorageEngine extends Engine {

    /**
     * Acquire the {@link ScheduleAppStorage} instance.
     *
     * @return the {@link ScheduleAppStorage} instance.
     */
    default <T> ScheduleAppStorage<T> scheduleAppStorage() {
        return this.beanFactory().getBean(ScheduleAppStorage.class);
    }

    /**
     * Acquire the {@link ScheduleClientStorage} instance.
     *
     * @return the {@link ScheduleClientStorage} instance.
     */
    default <T> ScheduleClientStorage<T> scheduleClientStorage() {
        return this.beanFactory().getBean(ScheduleClientStorage.class);
    }

    /**
     * Acquire the {@link ScheduleJobStorage} instance.
     *
     * @return the {@link ScheduleJobStorage} instance.
     */
    default <T> ScheduleJobStorage<T> scheduleJobStorage() {
        return this.beanFactory().getBean(ScheduleJobStorage.class);
    }

    /**
     * Acquire the {@link ScheduleLockStorage} instance.
     *
     * @return the {@link ScheduleLockStorage} instance.
     */
    default <T> ScheduleLockStorage<T> scheduleLockStorage() {
        return this.beanFactory().getBean(ScheduleLockStorage.class);
    }

    /**
     * Acquire the {@link SystemRoleStorage} instance.
     *
     * @return the {@link SystemRoleStorage} instance.
     */
    default <T> SystemRoleStorage<T> systemRoleStorage() {
        return this.beanFactory().getBean(SystemRoleStorage.class);
    }

    /**
     * Acquire the {@link SystemUserStorage} instance.
     *
     * @return the {@link SystemUserStorage} instance.
     */
    default <T> SystemUserStorage<T> systemUserStorage() {
        return this.beanFactory().getBean(SystemUserStorage.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire the {@link ScheduleJobChainStorage} instance.
     *
     * @return the {@link ScheduleJobChainStorage} instance.
     */
    default <T> ScheduleJobChainStorage<T> scheduleJobChainStorage() {
        return this.beanFactory().getBean(ScheduleJobChainStorage.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire the {@link ScheduleJobTriggerRecordStorage} instance.
     *
     * @return the {@link ScheduleJobTriggerRecordStorage} instance.
     */
    default <T> ScheduleJobTriggerRecordStorage<T> scheduleJobTriggerRecordStorage() {
        return this.beanFactory().getBean(ScheduleJobTriggerRecordStorage.class);
    }

    /**
     * Acquire the {@link ScheduleJobTriggerStatStorage} instance.
     *
     * @return the {@link ScheduleJobTriggerStatStorage} instance.
     */
    default <T> ScheduleJobTriggerStatStorage<T> scheduleJobTriggerStatStorage() {
        return this.beanFactory().getBean(ScheduleJobTriggerStatStorage.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire the {@link SystemRoleAppLinkStorage} instance.
     *
     * @return the {@link SystemRoleAppLinkStorage} instance.
     */
    default <T> SystemRoleAppLinkStorage<T> systemRoleAppLinkStorage() {
        return this.beanFactory().getBean(SystemRoleAppLinkStorage.class);
    }

    /**
     * Acquire the {@link SystemUserRoleLinkStorage} instance.
     *
     * @return the {@link SystemUserRoleLinkStorage} instance.
     */
    default <T> SystemUserRoleLinkStorage<T> systemUserRoleLinkStorage() {
        return this.beanFactory().getBean(SystemUserRoleLinkStorage.class);
    }
}
