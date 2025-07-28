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
package io.github.photowey.riff.business.job.core.domain.payload;

import java.io.Serial;

import io.github.photowey.riff.core.domain.entity.ScheduleJob;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobAddPayload}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScheduleJobAddPayload extends AbstractSchedulePayload<ScheduleJob> {

    @Serial
    private static final long serialVersionUID = -3642962623917300066L;

    /**
     * JobCode
     */
    private String jobCode;
    /**
     * JobName
     */
    private String jobName;
    /**
     * JobType 1:HandlerJob 2:ScriptJob 4:HttpJob
     */
    private Integer jobType;
    /**
     * HandlerName
     */
    private String handlerName;
    /**
     * DeclaredClass
     */
    private String declaredClass;
    /**
     * Method
     */
    private String method;
    /**
     * Arguments
     */
    private String arguments;

    // ----------------------------------------------------------------

    /**
     * AppID
     */
    @Schema(hidden = true)
    private Long appId;
}
