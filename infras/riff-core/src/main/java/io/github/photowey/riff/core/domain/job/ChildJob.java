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
package io.github.photowey.riff.core.domain.job;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ChildJob}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildJob implements Serializable {

    @Serial
    private static final long serialVersionUID = 3912896768841152941L;

    /**
     * The ID of the child job, if the child job has been created.
     */
    private Long childId;

    /**
     * The code of the child job, if the child job has been defined.
     */
    private String childCode;

    /**
     * The condition that triggers the execution of the child job.
     */
    private String triggerCondition;

    /**
     * Additional context or parameters passed when triggering the child job.
     * Typically includes key-value pairs of runtime variables, environment data,
     * or input parameters. Supports {@code SpEL} (Spring Expression Language)
     * for dynamic evaluation.
     */
    private String triggerContext;

    // ----------------------------------------------------------------

    public Long childId() {
        return childId;
    }

    public String childCode() {
        return childCode;
    }

    public String triggerCondition() {
        return triggerCondition;
    }

    public String triggerContext() {
        return triggerContext;
    }
}
