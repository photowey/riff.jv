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
package io.github.photowey.riff.business.job.service;

import io.github.photowey.riff.business.job.core.domain.payload.ScheduleAppAddPayload;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;

/**
 * {@code ScheduleAppService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
public interface ScheduleAppService {

    /**
     * Registers a new schedule application
     *
     * <p>
     * This method creates and registers a schedule application instance based on the provided
     * registration payload. The operation must be idempotent, meaning that calling this method
     * multiple times with the same {@link ScheduleAppAddPayload} will only create one corresponding
     * schedule application. Subsequent calls will return the existing application instance or an
     * equivalent success result, without creating duplicates.
     *
     * <p>
     * Idempotency is typically achieved by using a unique identifier (application code).
     * The service implementation should check for this uniqueness to prevent duplicate registrations.
     *
     * @param payload The payload {@link ScheduleAppAddPayload}
     *                containing the details of the schedule application to be registered
     * @return The registered schedule application instance {@link ScheduleApp}
     */
    ScheduleApp register(ScheduleAppAddPayload payload);
}
