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
package io.github.photowey.riff.business.job.web.controller.openapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.photowey.riff.business.job.core.domain.payload.ScheduleClientAddPayload;
import io.github.photowey.riff.business.job.service.ScheduleClientService;
import io.github.photowey.riff.core.domain.dto.ScheduleClientDTO;
import io.github.photowey.riff.core.domain.entity.ScheduleClient;
import io.github.photowey.riff.infras.model.result.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code OpenAPIScheduleClientController}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Slf4j
@RestController
@RequestMapping("/openapi/v1/schedule/client")
@SuppressWarnings("all")
public class OpenAPIScheduleClientController {

    @Autowired
    private ScheduleClientService scheduleClientService;

    /**
     * POST :/register
     *
     * <p>
     * Registers a schedule client.
     *
     * <p><strong>Idempotency:</strong> This method is idempotent. Repeated calls with the same payload
     * (i.e., identical business unique identifiers) will not create duplicate resources.
     * Instead, the result of the previously created resource will be returned, ensuring that
     * multiple invocations have the same effect as a single invocation.</p>
     *
     * <p><strong>HTTP Method:</strong> POST</p>
     *
     * <p><strong>Path:</strong> /register</p>
     *
     * @param payload The request payload containing registration data, of type {@link ScheduleClientAddPayload}.
     * @return Returns An {@link ApiResult} encapsulating a {@link ScheduleClientDTO} object.
     */
    @PostMapping("/register")
    public ApiResult<ScheduleClientDTO> register(@RequestBody @Validated ScheduleClientAddPayload payload) {
        ScheduleClient tt = this.scheduleClientService.register(payload);

        return ApiResult.success(tt.toDto());
    }
}
