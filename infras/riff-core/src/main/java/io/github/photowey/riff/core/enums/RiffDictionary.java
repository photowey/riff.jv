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
package io.github.photowey.riff.core.enums;

import io.github.photowey.riff.infras.common.util.Objects;

/**
 * {@code RiffDictionary}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/30
 */
public enum RiffDictionary {

    ;

    public enum Client {

        ;

        public enum Status {

            // CLIENT STATUS: 1:Online 2:Unhealthy 4:Suspect 8:Offline

            ONLINE("ONLINE", "online", 1),
            UNHEALTHY("UNHEALTHY", "unhealthy", 2),
            SUSPECT("SUSPECT", "suspect", 4),
            OFFLINE("OFFLINE", "offline", 8),

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
                    throw new NullPointerException("code is required.");
                }

                for (Status status : values()) {
                    if (status.code().equalsIgnoreCase(code)) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid client's status code.");
            }

            public static Status valueOf(int value) {
                for (Status status : values()) {
                    if (status.value() == value) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid client's status value.");
            }
        }
    }

    public enum Job {

        ;

        public enum TriggerStatus {

            // JOB TRIGGER-STATUS: 1: Not started 2: In progress 3: Completed

            NOT_STARTED("NOT_STARTED", "not.started", 1),
            IN_PROGRESS("IN_PROGRESS", "in.process", 2),
            COMPLETED("COMPLETED", "completed", 3),

            ;

            private final String name;
            private final String code;
            private final int value;

            TriggerStatus(String name, String code, int value) {
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

            public static TriggerStatus codeOf(String code) {
                if (Objects.isNull(code)) {
                    throw new NullPointerException("code is required.");
                }

                for (TriggerStatus status : values()) {
                    if (status.code().equalsIgnoreCase(code)) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid job's trigger status code.");
            }

            public static TriggerStatus valueOf(int value) {
                for (TriggerStatus status : values()) {
                    if (status.value() == value) {
                        return status;
                    }
                }

                throw new RuntimeException("Invalid job's trigger status value.");
            }
        }
    }

    public enum Schedule {

        ;

        public enum Type {

            // SCHEDULE TYPE 1:Schedule once 2:Cron 3:FixedRate 4:FixedDelay

            SCHEDULE_ONCE("SCHEDULE_ONCE", "once", 1),
            CRON("CRON", "cron", 2),
            FIXED_RATE("FIXED_RATE", "fixedrate", 3),
            FIXED_DELAY("FIXED_DELAY", "fixeddelay", 4),

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

            public static Type codeOf(String code) {
                if (Objects.isNull(code)) {
                    throw new NullPointerException("code is required.");
                }

                for (Type type : values()) {
                    if (type.code().equalsIgnoreCase(code)) {
                        return type;
                    }
                }

                throw new RuntimeException("Invalid job's schedule type code.");
            }

            public static Type valueOf(int value) {
                for (Type type : values()) {
                    if (type.value() == value) {
                        return type;
                    }
                }

                throw new RuntimeException("Invalid job's schedule type value.");
            }
        }
    }

}
