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
package io.github.photowey.riff.core.domain.entity;

import io.github.photowey.riff.middleware.database.core.domain.entity.AbstractTenantEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code ScheduleApp}.
 * |- riff_schedule_app
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
public class ScheduleApp extends AbstractTenantEntity {

    /**
     * AppCode
     */
    private String appCode;
    /**
     * AppName
     */
    private String appName;
    /**
     * AccessKey
     */
    private String accessKey;
    /**
     * AccessSecret
     */
    private String accessSecret;
    /**
     * Cluster
     */
    private String cluster;
    /**
     * ConfiguratorNamespace
     * |- Nullable
     * |- e.g.: {@code Nacos#Namespace}
     */
    private String configuratorNamespace;
    /**
     * ConfiguratorGroup
     * |- Nullable
     * |- e.g.: {@code Nacos#Group}
     */
    private String configuratorGroup;

    // ----------------------------------------------------------------

    public String appCode() {
        return this.appCode;
    }

    public String appName() {
        return this.appName;
    }

    public String accessKey() {
        return this.accessKey;
    }

    public String accessSecret() {
        return this.accessSecret;
    }

    public String cluster() {
        return this.cluster;
    }

    public String configuratorNamespace() {
        return this.configuratorNamespace;
    }

    public String configuratorGroup() {
        return this.configuratorGroup;
    }

}
