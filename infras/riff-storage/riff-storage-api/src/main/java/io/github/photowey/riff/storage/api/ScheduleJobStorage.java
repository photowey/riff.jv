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
package io.github.photowey.riff.storage.api;

import java.util.Optional;

import io.github.photowey.riff.core.domain.entity.ScheduleJob;

/**
 * {@code ScheduleJobStorage}.
 *
 * @param <PO> The {@code Database} persistence entity type.
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/24
 */
public interface ScheduleJobStorage<PO> extends EntityStorage<ScheduleJob, PO> {

    /**
     * Test that the job(job code) exists
     *
     * @param job the current job {@link ScheduleJob}
     * @return the database entity {@link ScheduleJob}
     */
    Optional<ScheduleJob> testJobExists(ScheduleJob job);

    /**
     * Test that the job handler's handle method exists
     *
     * @param job the current job {@link ScheduleJob}
     * @return the database entity {@link ScheduleJob}
     */
    boolean testMethodNameExists(ScheduleJob job);
}
