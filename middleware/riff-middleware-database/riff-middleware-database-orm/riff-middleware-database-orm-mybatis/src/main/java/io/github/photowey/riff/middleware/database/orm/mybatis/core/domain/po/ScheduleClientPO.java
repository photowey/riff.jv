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
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import io.github.photowey.riff.middleware.database.orm.mybatis.core.domain.mybatisplus.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleClientPO}.
 * |- riff_schedule_client
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
@TableName("riff_schedule_client")
public class ScheduleClientPO extends AbstractTenantEntity {

    @Serial
    private static final long serialVersionUID = 4660310409776381322L;

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
    /**
     * HealthCheckSuccessCount
     */
    private Integer healthCheckSuccessCount;
    /**
     * HealthCheckFailureCount
     */
    private Integer healthCheckFailureCount;
    /**
     * ReceivedHeartbeatCount
     */
    private Integer receivedHeartbeatCount;
    /**
     * OnlineTime
     */
    private LocalDateTime onlineTime;
    /**
     * OfflineTime
     */
    private LocalDateTime offlineTime;

    // ----------------------------------------------------------------

    public Long appId() {
        return this.appId;
    }

    public Long jobId() {
        return this.jobId;
    }

    public Integer clientStatus() {
        return this.clientStatus;
    }

    public String serverIp() {
        return this.serverIp;
    }

    public Integer serverPort() {
        return this.serverPort;
    }

    public String serverProtocol() {
        return this.serverProtocol;
    }

    public String serverAddress() {
        return serverAddress;
    }

    public Integer healthCheckSuccessCount() {
        return this.healthCheckSuccessCount;
    }

    public Integer healthCheckFailureCount() {
        return this.healthCheckFailureCount;
    }

    public Integer receivedHeartbeatCount() {
        return this.receivedHeartbeatCount;
    }

    public LocalDateTime onlineTime() {
        return this.onlineTime;
    }

    public LocalDateTime offlineTime() {
        return this.offlineTime;
    }

}
