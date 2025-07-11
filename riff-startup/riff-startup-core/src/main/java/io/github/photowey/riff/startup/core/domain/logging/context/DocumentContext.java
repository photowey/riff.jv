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
package io.github.photowey.riff.startup.core.domain.logging.context;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code DocumentContext}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentContext implements Serializable {

    @Serial
    private static final long serialVersionUID = 1238866251476668122L;

    private static final String DEFAULT_DOCUMENT_INDEX_PATH = "doc.html";

    private String protocol;
    private String app;
    private String host;
    private String port;
    private String profileActive;
    private String contextPath;
    private String healthContextPath;

    // ----------------------------------------------------------------

    private String swaggerPath;
    private boolean swaggerEnabled;

    public static DocumentContextBuilder defaultContext() {
        return DocumentContext.builder().swaggerEnabled(false);
    }

    public static DocumentContextBuilder swaggerContext() {
        return swaggerContext(DEFAULT_DOCUMENT_INDEX_PATH);
    }

    public static DocumentContextBuilder swaggerContext(String swaggerPath) {
        return DocumentContext.builder()
            .swaggerEnabled(true)
            .swaggerPath(swaggerPath);
    }

    public static class DocumentContextBuilder {
        private String protocol;
        private String app;
        private String host;
        private String port;
        private String profileActive;
        private String contextPath;
        private String healthContextPath;
        private String swaggerPath;
        private boolean swaggerEnabled;

        DocumentContextBuilder() {
        }

        public DocumentContextBuilder protocol(final String protocol) {
            this.protocol = protocol;
            return this;
        }

        public DocumentContextBuilder app(final String app) {
            this.app = app;
            return this;
        }

        public DocumentContextBuilder host(final String host) {
            this.host = host;
            return this;
        }

        public DocumentContextBuilder port(final String port) {
            this.port = port;
            return this;
        }

        public DocumentContextBuilder profileActive(final String profileActive) {
            this.profileActive = profileActive;
            return this;
        }

        public DocumentContextBuilder contextPath(final String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public DocumentContextBuilder healthContextPath(final String healthContextPath) {
            this.healthContextPath = healthContextPath;
            return this;
        }

        public DocumentContextBuilder swaggerPath(final String swaggerPath) {
            this.swaggerPath = swaggerPath;
            return this;
        }

        public DocumentContextBuilder swaggerEnabled(final boolean swaggerEnabled) {
            this.swaggerEnabled = swaggerEnabled;
            return this;
        }

        public DocumentContext build() {
            return new DocumentContext(this.protocol, this.app, this.host, this.port,
                this.profileActive, this.contextPath, this.healthContextPath, this.swaggerPath,
                this.swaggerEnabled
            );
        }

        @Override
        public String toString() {
            return "DocumentContext.DocumentContextBuilder(protocol=" + this.protocol
                + ", app=" + this.app
                + ", host=" + this.host
                + ", port=" + this.port
                + ", profileActive=" + this.profileActive
                + ", contextPath=" + this.contextPath
                + ", healthContextPath=" + this.healthContextPath
                + ", swaggerPath=" + this.swaggerPath
                + ", swaggerEnabled=" + this.swaggerEnabled
                + ")";
        }
    }
}

