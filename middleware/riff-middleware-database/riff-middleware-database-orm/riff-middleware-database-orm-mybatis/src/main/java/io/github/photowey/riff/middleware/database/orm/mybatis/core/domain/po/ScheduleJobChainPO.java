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
package io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;

import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleJobChainPO}.
 * |- riff_schedule_job_chain
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("riff_schedule_job_chain")
public class ScheduleJobChainPO extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = 4202434336779122933L;

    /**
     * The ID of the parent job, or {@code 0} if this job has no parent.
     */
    private Long parentId;

    /**
     * The ID of the child job, or {@code 0} if no child job has been created.
     */
    private Long childId;

    /**
     * The code identifier of the child job, or {@code null} if the child job
     * has not been defined.
     */
    private String childCode;

    /**
     * The condition that determines when the child job should be triggered.
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

    public Long parentId() {
        return this.parentId;
    }

    public Long childId() {
        return this.childId;
    }

    public String childCode() {
        return childCode;
    }

    public String triggerCondition() {
        return this.triggerCondition;
    }

    public String triggerContext() {
        return this.triggerContext;
    }

}
