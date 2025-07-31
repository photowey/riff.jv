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
package io.github.photowey.riff.infras.common.enums;

/**
 * {@code CommonDictionary}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/31
 */
public enum CommonDictionary {

    ;

    public enum Boolean {

        // BOOLEAN VALUE 0: false 1: true

        FALSE("false", false, 0),
        TRUE("true", true, 1),

        ;

        private final String name;
        private final boolean bv;
        private final int value;

        Boolean(String name, boolean bv, int value) {
            this.name = name;
            this.bv = bv;
            this.value = value;
        }

        public String string() {
            return name;
        }

        public boolean bool() {
            return bv;
        }

        public int value() {
            return value;
        }
    }

}
