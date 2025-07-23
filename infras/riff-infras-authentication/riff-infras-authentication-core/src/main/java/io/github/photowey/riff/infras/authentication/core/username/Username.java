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
package io.github.photowey.riff.infras.authentication.core.username;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Username}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Username implements Serializable {

    @Serial
    private static final long serialVersionUID = 5477934765906380818L;

    private String tenant;
    private String platform;
    private String app;

    private String client;
    /**
     * 1: web | username
     * 2: oauthclient | accessKey
     * 3: cmder | accessKey
     */
    private Integer type;

    /**
     * Username | AccessKey
     */
    private String username;
    /**
     * Password | SecretSecret
     */
    private String password;

    public String compact() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static Username parse(String proxy) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
