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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import jakarta.validation.constraints.NotNull;

import io.github.photowey.riff.core.domain.entity.ScheduleClient;
import io.github.photowey.riff.core.enums.RiffDictionary;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.common.util.Uris;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleClientAddPayload}.
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
public class ScheduleClientAddPayload extends AbstractSchedulePayload<ScheduleClient> {

    @Serial
    private static final long serialVersionUID = -8449665949966343752L;

    private static final String SERVER_ADDRESS_TEMPLATE = "{}://{}:{}";

    /**
     * JobID
     */
    @NotNull(message = "The Job ID can't be NULL")
    @Schema(
        description = "The Job ID",
        example = "1947519918762827778",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long jobId;
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
     * ${serverProtocol}://${serverIp}:${serverPort}
     *
     * <p>
     * |- <a href="http://127.0.0.1:9527">http</a>
     *
     * <p>
     * |- <a href="grpc://127.0.0.1:7923">grpc</a>
     *
     * <p>
     * Others:
     * |- kafka://127.0.0.1:9092/${topic}?xxx=...
     *
     * <p>
     * |- rabbitmq://127.0.0.1:5672/${exchanger}?xxx=...
     */
    private String serverAddress;

    // ----------------------------------------------------------------

    /**
     * AppID
     */
    @Schema(hidden = true)
    private Long appId;

    // ----------------------------------------------------------------

    @Override
    public void checkActions() {
        super.checkActions();
    }

    private void checkAddress() {
        if (Strings.isNotEmpty(this.serverAddress)) {
            boolean ok = this.checkUriPattern(this.serverAddress);
            if (!ok) {
                throw new IllegalArgumentException("The client's serverAddress is invalid");
            }

            return;
        }

        this.checkServerInfo();
    }

    private void checkServerInfo() {
        if (Strings.isEmpty(this.serverIp)) {
            throw new IllegalArgumentException("The client's serverIp can't be NULL");
        }

        if (Objects.isEmpty(this.serverPort)) {
            throw new IllegalArgumentException("The client‘s serverPort can't be NULL");
        }

        if (Strings.isEmpty(this.serverProtocol)) {
            throw new IllegalArgumentException("The client Server PROTOCOL can't be NULL");
        }
    }

    // ----------------------------------------------------------------

    public ScheduleClient toScheduleClient() {
        this.tryCheckOrParseIfNecessary();

        // ScheduleApp(id,AK|AS) -> LoginUser
        LoginUser authenticated = LoginUserHolder.mustGet();

        return ScheduleClient.builder()
            .tenant(this.tenant)
            .platform(this.platform)
            .app(this.app)
            .jobId(this.jobId)
            .serverIp(this.serverIp)
            .serverPort(this.serverPort)
            .serverProtocol(this.serverProtocol)
            .serverAddress(this.serverAddress)
            .appId(authenticated.userId())
            // ----------------------------------------------------------------
            .clientStatus(RiffDictionary.Client.Status.ONLINE.value())
            // ----------------------------------------------------------------
            // @see io.github.photowey.riff.core.domain.entity.ScheduleClient#initBaseCounter
            //.healthCheckSuccessCount(0)
            //.healthCheckFailureCount(0)
            //.receivedHeartbeatCount(0)
            .onlineTime(LocalDateTime.now())
            //.offlineTime
            // ----------------------------------------------------------------
            .build();
    }

    public ScheduleClient toScheduleClient(Consumer<ScheduleClient> fx) {
        ScheduleClient tt = this.toScheduleClient();
        fx.accept(tt);

        return tt;
    }

    private void tryCheckOrParseIfNecessary() {
        if (Strings.isEmpty(this.serverAddress) && Strings.isNotEmpty(this.serverIp)) {
            this.serverAddress = StringFormatter.format(
                SERVER_ADDRESS_TEMPLATE, this.serverProtocol, this.serverIp, this.serverPort
            );
        }

        if (Strings.isNotEmpty(this.serverAddress) && Strings.isEmpty(this.serverIp)) {
            this.parseAddress();
        }
    }

    private void parseAddress() {
        try {
            URI uri = new URI(this.serverAddress);
            this.serverProtocol = uri.getScheme();
            this.serverIp = uri.getHost();
            this.serverPort = uri.getPort();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkUriPattern(String uri) {
        return Uris.checkPattern(uri);
    }
}
