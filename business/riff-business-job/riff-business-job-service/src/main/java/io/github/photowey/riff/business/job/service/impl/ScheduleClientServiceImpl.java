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
import io.github.photowey.riff.business.job.core.domain.payload.ScheduleClientAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleClientService;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.core.domain.entity.ScheduleClient;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleAppPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleClientPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobPO;
import io.github.photowey.riff.storage.api.ScheduleAppStorage;
import io.github.photowey.riff.storage.api.ScheduleClientStorage;
import io.github.photowey.riff.storage.api.ScheduleJobStorage;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code ScheduleClientServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Slf4j
@Service
public class ScheduleClientServiceImpl implements ScheduleClientService {

    @Autowired
    private ScheduleAppStorage<ScheduleAppPO> scheduleAppStorage;
    @Autowired
    private ScheduleJobStorage<ScheduleJobPO> scheduleJobStorage;
    @Autowired
    private ScheduleClientStorage<ScheduleClientPO> scheduleClientStorage;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleClient register(ScheduleClientAddPayload payload) {
        payload.preAction();
        ScheduleClient client = this.tryRegister(payload);
        payload.postAction();

        return client;
    }

    private ScheduleClient tryRegister(ScheduleClientAddPayload payload) {
        ScheduleClient tt = payload.toScheduleClient(this::preRegister);
        if (tt.determineIsRegistered()) {
            tt.initBaseCounter();
            this.scheduleClientStorage.save(tt);
        } else {
            this.scheduleClientStorage.updateById(tt);
        }
        this.postRegister(tt);

        return tt;
    }

    private void preRegister(ScheduleClient tt) {
        this.checkAppId(tt);
        this.checkJobId(tt.jobId());

        this.testClientExists(tt);
    }

    private void postRegister(ScheduleClient tt) {
        // TODO Not implemented
    }

    // ----------------------------------------------------------------

    private void testClientExists(ScheduleClient tt) {
        Optional<ScheduleClient> clientOpt = this.scheduleClientStorage.testClientExists(tt);
        if (clientOpt.isPresent()) {
            tt.setRegistered(1);
            tt.setId(clientOpt.get().id());
        }
    }

    // ----------------------------------------------------------------

    private void checkAppId(ScheduleClient tt) {
        Optional<ScheduleApp> appOpt = this.scheduleAppStorage.simpleQuery(tt.appId());
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

    private void checkJobId(Long jobId) {
        boolean exists = this.scheduleJobStorage.exists(jobId);
        AbstractJobExceptionChecker.checkTrue(exists, "The jobId not exists");
    }
}
