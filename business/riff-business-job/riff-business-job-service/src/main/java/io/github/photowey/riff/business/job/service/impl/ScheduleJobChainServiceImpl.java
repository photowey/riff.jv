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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.core.checker.exception.AbstractJobExceptionChecker;
import io.github.photowey.riff.business.job.core.constant.MessageConstants;
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleJobChainAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleJobChainService;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;
import io.github.photowey.riff.infras.common.enums.CommonDictionary;
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

    @Override
    public List<Long> cycleDetect(Long childId) {
        return this.storageEngine.scheduleJobChainStorage().cycleDetect(childId);
    }

    // ----------------------------------------------------------------

    private void preBatchAdd(List<ScheduleJobChain> chains) {
        chains.forEach(it -> {
            if (it.determineNeedCheckParent()) {
                this.checkParentId(it);
            }

            this.checkChildId(it);
            this.checkChildCode(it);

            it.initChildIdIfNecessary();
        });
    }

    private void postBatchAdd(List<ScheduleJobChain> chains) {

    }

    // ----------------------------------------------------------------

    private void refreshChildIdByCode(ScheduleJob job) {
        int affected = this.storageEngine.scheduleJobChainStorage().refreshChildIdByCode(job);
        if (log.isInfoEnabled()) {
            log.info("riff: job [{}:{}] added, refreshing job chain by job code, affected {} rows",
                job.id(),
                job.jobCode(),
                affected
            );
        }
    }

    private void refreshChildCodeById(ScheduleJob job) {
        int affected = this.storageEngine.scheduleJobChainStorage().refreshChildCodeById(job);

        if (log.isInfoEnabled()) {
            log.info("riff: job [{}:{}] added, refreshing job chain by id, affected {} rows",
                job.id(),
                job.jobCode(),
                affected
            );
        }
    }

    // ----------------------------------------------------------------

    private void checkParentId(ScheduleJobChain chain) {
        boolean exists = this.storageEngine.scheduleJobStorage().exists(chain.parentId());

        AbstractJobExceptionChecker.checkTrue(
            exists,
            MessageConstants.JobChain.ERROR_PARENT_JOB_NOT_EXISTS,
            chain.parentId()
        );
    }

    private void checkChildId(ScheduleJobChain chain) {
        if (Objects.isNull(chain.childId())) {
            return;
        }

        Optional<ScheduleJob> jobOpt = this.storageEngine.scheduleJobStorage().simpleQuery(chain.childId());
        if (jobOpt.isPresent()) {
            ScheduleJob child = jobOpt.get();
            if (Strings.isNotEmpty(chain.getChildCode())) {
                if (Strings.isNotEquals(child.jobCode(), chain.getChildCode())) {
                    AbstractJobExceptionChecker.throwUnchecked(
                        MessageConstants.JobChain.ERROR_CHILD_JOB_CODE_MISMATCH,
                        child.jobCode()
                    );
                }
            }

            chain.setChildCode(child.jobCode());
            chain.setCheckChildCode(CommonDictionary.Boolean.FALSE.value());

            return;
        }

        AbstractJobExceptionChecker.throwUnchecked(MessageConstants.Job.ERROR_CHILD_JOB_NOT_FOUND, chain.childId());
    }

    private void checkChildCode(ScheduleJobChain chain) {
        if (Strings.isEmpty(chain.childCode())) {
            return;
        }

        if (chain.determineNeedCheckChildCode()) {
            Optional<ScheduleJob> jobOpt = this.storageEngine.scheduleJobStorage().simpleQuery(chain.childCode());

            if (jobOpt.isPresent()) {
                ScheduleJob child = jobOpt.get();
                chain.setChildId(child.id());

                chain.setCheckChildCode(CommonDictionary.Boolean.FALSE.value());
            }

            AbstractJobExceptionChecker.throwUnchecked(
                MessageConstants.Job.ERROR_CHILD_JOB_NOT_FOUND, chain.childCode()
            );
        }
    }
}
