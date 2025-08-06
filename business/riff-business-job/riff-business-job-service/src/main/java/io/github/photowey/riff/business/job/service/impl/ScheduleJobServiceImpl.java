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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.core.checker.exception.AbstractJobExceptionChecker;
import io.github.photowey.riff.business.job.core.context.ScheduleContext;
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleJobAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleJobChainService;
import io.github.photowey.riff.business.job.service.ScheduleJobService;
import io.github.photowey.riff.business.job.service.calculator.TriggerTimeCalculator;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;
import io.github.photowey.riff.core.enums.RiffDictionary;
import io.github.photowey.riff.infras.common.datetime.Timestamps;
import io.github.photowey.riff.infras.common.enums.CommonDictionary;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;
import io.github.photowey.riff.storage.api.engine.StorageEngine;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code ScheduleJobService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Slf4j
@Service
public class ScheduleJobServiceImpl extends AbstractBeanFactoryHolder implements ScheduleJobService {

    @Autowired
    private StorageEngine storageEngine;

    @Autowired
    private ScheduleJobChainService scheduleJobChainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleJob register(ScheduleJobAddPayload payload) {
        // TODO lock.lock()?
        payload.preAction();
        ScheduleJob tt = this.tryRegister(payload);
        payload.postAction();

        return tt;
    }

    private ScheduleJob tryRegister(ScheduleJobAddPayload payload) {
        ScheduleJob tt = payload.toScheduleJob(this::preRegister);
        if (tt.determineIsRegistered()) {
            // TODO update Or return?
            this.storageEngine.scheduleJobStorage().updateById(tt);
        } else {
            this.storageEngine.scheduleJobStorage().save(tt);
        }

        this.postRegister(tt);

        return tt;
    }

    private void preRegister(ScheduleJob tt) {
        this.checkAppId(tt);
        this.checkMethodName(tt);

        this.testJobExists(tt);

        this.determineTriggerInfo(tt);
    }

    private void postRegister(ScheduleJob tt) {
        this.tryAddScheduleJobChainIfNecessary(tt);
        this.refreshParentScheduleJobChildrenIdIfNecessary(tt);

        this.cycleDetect(tt);
    }

    // ----------------------------------------------------------------

    private void tryAddScheduleJobChainIfNecessary(ScheduleJob tt) {
        if (tt.determineHasChildJobs()) {
            tt.childJobs().forEach(it -> {
                this.injectChainBase(tt, it);
            });
            this.batchAddScheduleJobChains(tt);
        }
    }

    private void refreshParentScheduleJobChildrenIdIfNecessary(ScheduleJob tt) {
        if (tt.determineHasChildJobs()) {
            this.refreshScheduleJobChain(tt);
        }
    }

    private void cycleDetect(ScheduleJob tt) {
        List<Long> parentIds = this.scheduleJobChainService.cycleDetect(tt.id());
        Set<Long> distinctParentIds = new HashSet<>(parentIds);

        if (distinctParentIds.size() < parentIds.size()) {
            AbstractJobExceptionChecker.throwUnchecked("A cycle has been detected in the job chain");
        }

        parentIds.clear();
        distinctParentIds.clear();

        parentIds = null;
        distinctParentIds = null;
    }

    private void injectChainBase(ScheduleJob tt, ScheduleJobChain it) {
        it.setParentId(tt.id());
        it.setCheckParent(CommonDictionary.Boolean.FALSE.value());

        it.setTenant(tt.tenant());
        it.setPlatform(tt.platform());
        it.setApp(tt.app());
    }

    // ----------------------------------------------------------------

    private void batchAddScheduleJobChains(ScheduleJob tt) {
        this.scheduleJobChainService.batchAdd(tt.childJobs());
    }

    private void refreshScheduleJobChain(ScheduleJob tt) {
        this.scheduleJobChainService.refreshJobChains(tt);
    }

    // ----------------------------------------------------------------

    private void checkAppId(ScheduleJob tt) {
        Optional<ScheduleApp> appOpt = this.storageEngine.scheduleAppStorage().simpleQuery(tt.appId());
        if (appOpt.isPresent()) {
            ScheduleApp image = appOpt.get();
            tt.setTenant(image.tenant());
            tt.setPlatform(image.platform());
            tt.setApp(image.app());

            tt.injectTenantBase(image);

            return;
        }

        AbstractJobExceptionChecker.throwUnchecked("The appId not exists");
    }

    /**
     * App
     * |- Job
     * |- |- Handler
     * |- |- |- Method
     *
     * @param tt {@link ScheduleJob}
     */
    private void checkMethodName(ScheduleJob tt) {
        boolean methodNameExists = this.storageEngine.scheduleJobStorage().testMethodNameExists(tt);
        AbstractJobExceptionChecker.checkFalse(methodNameExists, "The method name:[{}#{}] is duplicated",
            tt.declaredClass(),
            tt.method()
        );
    }

    private void testJobExists(ScheduleJob tt) {
        Optional<ScheduleJob> jobOpt = this.storageEngine.scheduleJobStorage().testJobExists(tt);
        if (jobOpt.isPresent()) {
            tt.setRegistered(CommonDictionary.Boolean.TRUE.value());
            tt.setId(jobOpt.get().id());
        }
    }

    private void determineTriggerInfo(ScheduleJob tt) {
        this.determineTriggerStatus(tt);
        this.parseScheduleContext(tt);
    }

    private void determineTriggerStatus(ScheduleJob tt) {
        tt.setTriggerStatus(RiffDictionary.Job.TriggerStatus.NOT_STARTED.value());
    }

    private void parseScheduleContext(ScheduleJob tt) {
        ScheduleContext ctx = ScheduleContext.parse(tt.scheduleContext());

        Map<String, TriggerTimeCalculator> beans =
            this.listableBeanFactory().getBeansOfType(TriggerTimeCalculator.class);
        List<TriggerTimeCalculator> triggerTimeCalculators = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(triggerTimeCalculators);

        // The base time for the trigger.
        tt.setNow(Timestamps.trimTail(LocalDateTime.now()));

        for (TriggerTimeCalculator triggerTimeCalculator : triggerTimeCalculators) {
            if (triggerTimeCalculator.supports(ctx.type())) {
                triggerTimeCalculator.handle(ctx, tt);
                return;
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }
}
