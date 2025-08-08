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

import io.github.photowey.riff.business.job.core.domain.payload.ScheduleAppAddPayload;
import io.github.photowey.riff.business.job.core.secret.OauthSecret;
import io.github.photowey.riff.business.job.service.ScheduleAppService;
import io.github.photowey.riff.core.domain.entity.ScheduleApp;
import io.github.photowey.riff.infras.common.enums.CommonDictionary;
import io.github.photowey.riff.storage.api.engine.StorageEngine;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code ScheduleAppServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Slf4j
@Service
public class ScheduleAppServiceImpl implements ScheduleAppService {

    @Autowired
    private StorageEngine storageEngine;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleApp register(ScheduleAppAddPayload payload) {
        // TODO lock.lock()?
        payload.preAction();
        ScheduleApp app = this.tryRegister(payload);
        payload.postAction();

        return app;
    }

    private ScheduleApp tryRegister(ScheduleAppAddPayload payload) {
        ScheduleApp tt = payload.toScheduleApp(this::preRegister);
        if (tt.determineIsRegistered()) {
            // TODO Update or return?
            this.storageEngine.scheduleAppStorage().updateById(tt);
        } else {
            this.initAccessKey(tt);
            this.storageEngine.scheduleAppStorage().save(tt);
        }
        this.postRegister(tt);

        return tt;
    }

    private void preRegister(ScheduleApp tt) {
        // TODO Not implemented
        Optional<ScheduleApp> appOpt = this.storageEngine.scheduleAppStorage().simpleQuery(tt.appCode());
        if (appOpt.isPresent()) {
            ScheduleApp app = appOpt.get();
            tt.setId(app.id());
            tt.setAccessKey(app.accessKey());

            tt.setRegistered(CommonDictionary.Boolean.TRUE.value());
        }
    }

    private void postRegister(ScheduleApp tt) {
        if (log.isInfoEnabled()) {
            log.info("riff: registered a schedule app, id: [{}], appCode: [{}]", tt.id(), tt.appCode());
        }
    }

    // ----------------------------------------------------------------

    private void initAccessKey(ScheduleApp tt) {
        String accessKey = this.genAccessKey();
        String accessSecret = this.genAccessSecret(accessKey);

        tt.setAccessKey(accessKey);
        tt.setAccessSecret(accessSecret);
    }

    private String genAccessKey() {
        return OauthSecret.genAccessKey();
    }

    private String genAccessSecret(String accessKey) {
        return OauthSecret.genAccessSecret(accessKey);
    }
}
