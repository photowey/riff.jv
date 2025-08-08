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
package io.github.photowey.riff.middleware.database.orm.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.po.ScheduleJobChainPO;
import io.github.photowey.riff.middleware.database.orm.mybatis.ext.BatchRepositoryExt;

/**
 * {@code ScheduleJobChainRepository}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface ScheduleJobChainRepository extends BatchRepositoryExt<ScheduleJobChainPO> {

    /**
     * Physical delete
     *
     * @param id the id
     */
    void physicalDelete(@Param("id") Long id);

    /**
     * Refresh childId by jobCode
     *
     * @param childId   the child job ID
     * @param childCode the child job code
     * @return the affected rows
     */
    int refreshChildIdByCode(@Param("childId") Long childId, @Param("child_code") String childCode);

    /**
     * Refresh childCode by childId
     *
     * @param childId   the child job ID
     * @param childCode the child job code
     * @return the affected rows
     */
    int refreshChildCodeById(@Param("childId") Long childId, @Param("childCode") String childCode);

    /**
     * Detects cyclic references in the parent-child hierarchy.
     *
     * <p>
     * This method traverses upward from the specified child job, collecting all ancestor job IDs
     * to identify if a cycle exists (i.e., a job indirectly becomes its own ancestor).
     *
     * @param childId the ID of the child job to start the cycle detection from (must not be null)
     * @return an ordered list of parent job IDs from direct parent to root ancestor;
     */
    List<Long> cycleDetect(@Param("childId") Long childId);
}
