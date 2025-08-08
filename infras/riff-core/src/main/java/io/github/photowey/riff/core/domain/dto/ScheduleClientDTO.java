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
package io.github.photowey.riff.core.domain.dto;

import java.io.Serial;

import io.github.photowey.riff.middleware.database.core.domain.dto.AbstractTenantDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleClientDTO}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScheduleClientDTO extends AbstractTenantDTO {

    @Serial
    private static final long serialVersionUID = 494588000142144375L;

    /**
     * AppID
     */
    private Long appId;
    /**
     * JobID
     */
    private Long jobId;
    /**
     * ClientStatus 1:Online 2:Unhealthy 4:Suspect 8:Offline
     */
    private Integer clientStatus;
    /**
     * ServerIP
     */
    private String serverIp;
    /**
     * ServerPort
     */
    private Integer serverPort;
    /**
     * ServerProtocol
     */
    private String serverProtocol;
    /**
     * ServerAddress
     */
    private String serverAddress;
}
