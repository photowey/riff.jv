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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.photowey.riff.business.job.core.checker.exception.AbstractJobExceptionChecker;
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleJobAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleJobService;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.core.domain.entity.ScheduleJob;
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
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private StorageEngine storageEngine;

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
            // update Or return?
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
    }

    private void postRegister(ScheduleJob tt) {
        // TODO NOT implemented
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
            tt.setRegistered(1);
            tt.setId(jobOpt.get().id());
        }
    }
}
