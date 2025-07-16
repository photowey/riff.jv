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
package io.github.photowey.riff.infras.model.enums;

/**
 * {@code ApiDictionary}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
public enum ApiDictionary {

    ;

    /**
     * {@code API} return code.
     */
    public enum Status {

        // STATUS

        API_OK("OK", "200000"),
        API_FAILED("FAILED", "500000"),

        API_BAD_REQUEST("BAD_REQUEST", "400000"),
        API_UNAUTHORIZED("UNAUTHORIZED", "401000"),
        API_FORBIDDEN("FORBIDDEN", "403000"),
        API_NOT_FOUND("NOT_FOUND", "404000"),

        API_INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "500001"),
        API_BAD_GATEWAY("BAD_GATEWAY", "502000"),
        API_GATEWAY_TIMEOUT("GATEWAY_TIMEOUT", "504000"),

        ;

        private final String name;
        private final String value;

        Status(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String message() {
            return name;
        }

        public String value() {
            return value;
        }

        public static boolean isSuccessful(String code) {
            return API_OK.value().equals(code);
        }
    }
}
