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
package io.github.photowey.riff.business.job.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import io.github.photowey.riff.business.job.AbstractLocalTest;
import io.github.photowey.riff.business.job.TestJob;
import io.github.photowey.riff.core.domain.entity.ScheduleJobChain;

/**
 * {@code ScheduleJobChainStorageTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/06
 */
@SpringBootTest(classes = TestJob.class)
//@TestPropertySource(properties = "ci.spring.datasource.access.enabled=true")
@EnabledIf(expression = "${ci.spring.datasource.access.enabled}", loadContext = true)
class ScheduleJobChainStorageTest extends AbstractLocalTest {

    @Test
    void testBatchSave() {
        List<ScheduleJobChain> chains = new ArrayList<>();

        ScheduleJobChain chain1 = ScheduleJobChain.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .parentId(1950590661197238276L)
            .childId(1950590661197238273L)
            .childCode("io.github.photowey.riff.distributed.job.1")
            .build();

        ScheduleJobChain chain2 = ScheduleJobChain.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .parentId(1950590661197238273L)
            .childId(1950590661197238274L)
            .childCode("io.github.photowey.riff.distributed.job.2")
            .build();

        ScheduleJobChain chain3 = ScheduleJobChain.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .parentId(1950590661197238274L)
            .childId(1950590661197238275L)
            .childCode("io.github.photowey.riff.distributed.job.3")
            .build();

        ScheduleJobChain chain4 = ScheduleJobChain.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .parentId(1950590661197238275L)
            .childId(1950590661197238276L)
            .childCode("io.github.photowey.riff.distributed.job.4")
            .build();

        chains.add(chain1);
        chains.add(chain2);
        chains.add(chain3);
        chains.add(chain4);

        this.storageEngine.scheduleJobChainStorage().batchSave(chains);
    }

    @Test
    void testCycleDetect() {
        Long childId = 1950590661197238275L;
        List<Long> parentIds = this.storageEngine.scheduleJobChainStorage().cycleDetect(childId);
        Set<Long> distinctParentIds = new HashSet<>(parentIds);

        Assertions.assertTrue(distinctParentIds.size() < parentIds.size());
    }
}
