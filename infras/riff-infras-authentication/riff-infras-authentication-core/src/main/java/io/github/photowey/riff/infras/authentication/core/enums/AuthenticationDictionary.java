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
package io.github.photowey.riff.infras.authentication.core.enums;

import io.github.photowey.riff.infras.common.util.Objects;

/**
 * {@code AuthenticationDictionary}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public enum AuthenticationDictionary {

    ;

    public enum Authentication {

        ;

        public enum Status {

            // AUTHENTICATION STATUS

            UNAUTHENTICATED("UNAUTHENTICATED", "unauthenticated", 1),
            AUTHENTICATING("AUTHENTICATING", "authenticating", 2),
            AUTHENTICATED("AUTHENTICATED", "authenticated", 4),
            AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "authentication.failed", 8),

            ;

            private final String name;
            private final String code;
            private final int value;

            Status(String name, String code, int value) {
                this.name = name;
                this.code = code;
                this.value = value;
            }

            public String wrap() {
                return name;
            }

            public String code() {
                return code;
            }

            public int value() {
                return value;
            }

            public static Status codeOf(String code) {
                if (Objects.isNull(code)) {
                    throw new NullPointerException("code can't be null");
                }

                for (Status status : values()) {
                    if (status.code().equalsIgnoreCase(code)) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid Authentication code.");
            }

            public static Status valueOf(int value) {
                for (Status status : values()) {
                    if (status.value() == value) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid Authentication value.");
            }

            public static boolean determineIsUnAuthenticated(int value) {
                return Status.UNAUTHENTICATED.value() == value;
            }

            public static boolean determineIsAuthenticated(int value) {
                return Status.AUTHENTICATED.value() == value;
            }
        }
    }

    public enum User {

        ;

        public enum Type {

            // USER TYPE

            /**
             * |- Username
             * |- Password
             */
            BOSS("BOSS", "boss", 1),
            /**
             * |- AccessKey
             * |- AccessSecret
             */
            OAUTH_CLIENT("OAUTH_CLIENT", "oauthclient", 2),

            ;

            private final String name;
            private final String code;
            private final int value;

            Type(String name, String code, int value) {
                this.name = name;
                this.code = code;
                this.value = value;
            }

            public String wrap() {
                return name;
            }

            public String code() {
                return code;
            }

            public int value() {
                return value;
            }

            public static Type valueOf(int value) {
                for (Type type : values()) {
                    if (type.value() == value) {
                        return type;
                    }
                }

                throw new RuntimeException("riff: Invalid user type value.");
            }
        }

        public enum Status {

            // USER STATUS

            UNACTIVATED("UNACTIVATED", "unactivated", 1),
            /**
             * The Default.
             */
            ACTIVATED("ACTIVATED", "activated", 2),
            FROZEN("FROZEN", "frozen", 4),
            FORBIDDEN("FORBIDDEN", "forbidden", 8),
            EXPIRED("EXPIRED", "expired", 16),
            LOCKED("LOCKED", "locked", 32),

            ;

            private final String name;
            private final String code;
            private final int value;

            Status(String name, String code, int value) {
                this.name = name;
                this.code = code;
                this.value = value;
            }

            public String wrap() {
                return name;
            }

            public String code() {
                return code;
            }

            public int value() {
                return value;
            }

            public static Status codeOf(String code) {
                if (Objects.isNull(code)) {
                    throw new NullPointerException("riff: code can't be null");
                }

                for (Status status : values()) {
                    if (status.code().equalsIgnoreCase(code)) {
                        return status;
                    }
                }

                throw new RuntimeException("riff: Invalid user status code.");
            }

            public static Status valueOf(int value) {
                for (Status status : values()) {
                    if (status.value() == value) {
                        return status;
                    }
                }

                throw new RuntimeException("riff: Invalid user status value.");
            }

            public static boolean determineIsUnActivated(int status) {
                return Status.UNACTIVATED.value() == status;
            }

            public static boolean determineIsActivated(int status) {
                return Status.ACTIVATED.value() == status;
            }

            public static boolean determineIsFrozen(int status) {
                return Status.FROZEN.value() == status;
            }

            public static boolean determineIsForbidden(int status) {
                return Status.FORBIDDEN.value() == status;
            }

            public static boolean determineIsExpired(int status) {
                return Status.EXPIRED.value() == status;
            }
        }
    }


    public enum Client {

        // Client 1:WEB 2:OAuth

        WEB("WEB", "web", "web", 1),
        OAUTH_CLIENT("OAUTH", "oauth", "oauth", 2),
        UNKNOWN("Unknown", "unknown", "unknown", 1 << 30),

        ;

        private final String name;
        private final String logic;
        private final String code;
        private final int value;

        Client(String name, String logic, String code, int value) {
            this.name = name;
            this.logic = logic;
            this.code = code;
            this.value = value;
        }

        public String wrap() {
            return name;
        }

        public String logic() {
            return logic;
        }

        public String code() {
            return code;
        }

        public int value() {
            return value;
        }

        // ----------------------------------------------------------------

        public boolean determineIsNotUnknown() {
            return !this.determineIsUnknown();
        }

        public boolean determineIsUnknown() {
            return Client.UNKNOWN.value() == this.value();
        }

        // ----------------------------------------------------------------

        public static Client codeOf(String code) {
            if (Objects.isNull(code)) {
                throw new NullPointerException("riff: code can't be null");
            }

            for (Client client : values()) {
                if (client.code().equalsIgnoreCase(code)) {
                    return client;
                }
            }

            throw new RuntimeException("riff: Invalid Client code.");
        }

        public static Client valueOf(int value) {
            for (Client client : values()) {
                if (client.value() == value) {
                    return client;
                }
            }

            throw new RuntimeException("riff: Invalid Client value.");
        }
    }
}
