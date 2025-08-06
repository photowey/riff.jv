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

import java.util.List;

import io.github.photowey.riff.business.job.core.domain.payload.ScheduleJobChainAddPayload;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;

/**
 * {@code ScheduleJobChainService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
public interface ScheduleJobChainService {

    /**
     * Add a new {@link ScheduleJobChain}
     *
     * @param payload the payload {@link ScheduleJobChainAddPayload}
     * @return the {@link ScheduleJobChain}
     */
    ScheduleJobChain add(ScheduleJobChainAddPayload payload);

    /**
     * Batch add job chains
     *
     * @param chains the job chains {@link ScheduleJobChain}
     */
    void batchAdd(List<ScheduleJobChain> chains);

    /**
     * Refresh the job chains
     *
     * @param job the job {@link ScheduleJob}
     */
    void refreshJobChains(ScheduleJob job);

    List<Long> cycleDetect(Long childId);
}
