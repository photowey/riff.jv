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
package io.github.photowey.riff.business.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.core.checker.exception.AbstractJobExceptionChecker;
import io.github.photowey.riff.business.job.core.constant.JobMessageConstants;
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleJobChainAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleJobChainService;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.storage.api.engine.StorageEngine;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code ScheduleJobChainServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Slf4j
@Service
public class ScheduleJobChainServiceImpl implements ScheduleJobChainService {

    @Autowired
    private StorageEngine storageEngine;

    @Override
    public ScheduleJobChain add(ScheduleJobChainAddPayload payload) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<ScheduleJobChain> chains) {
        this.preBatchAdd(chains);
        this.storageEngine.scheduleJobChainStorage().batchSave(chains);
        this.postBatchAdd(chains);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshJobChains(ScheduleJob job) {
        this.refreshChildIdByCode(job);
        this.refreshChildCodeById(job);
    }

    // ----------------------------------------------------------------

    private void preBatchAdd(List<ScheduleJobChain> chains) {
        chains.forEach(it -> {
            if (it.determineNeedCheckParent()) {
                this.checkParentId(it);
            }

            this.checkChildId(it);
            this.checkChildCode(it);
        });
    }

    private void postBatchAdd(List<ScheduleJobChain> chains) {

    }

    // ----------------------------------------------------------------

    private void refreshChildIdByCode(ScheduleJob job) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private void refreshChildCodeById(ScheduleJob job) {

    }

    // ----------------------------------------------------------------

    private void checkParentId(ScheduleJobChain chain) {
        boolean exists = this.storageEngine.scheduleJobStorage().exists(chain.parentId());

        AbstractJobExceptionChecker.checkTrue(
            exists,
            JobMessageConstants.ERROR_PARENT_JOB_NOT_EXISTS, chain.parentId()
        );
    }

    private void checkChildId(ScheduleJobChain chain) {
        if (Objects.isNull(chain.childId())) {
            return;
        }

        // TODO Not implemented
    }

    private void checkChildCode(ScheduleJobChain chain) {
        if (Strings.isEmpty(chain.childCode())) {
            return;
        }

        // TODO Not implemented
    }
}
