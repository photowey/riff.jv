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
package io.github.photowey.riff.infras.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code StringsTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
class StringsTest {

    @Test
    void testDefaultIfEmpty() {
        Assertions.assertEquals("Hello,Riff",
            Strings.defaultIfEmpty(null, "Hello,Riff"));

        Assertions.assertEquals("Hello,Riff",
            Strings.defaultIfEmpty("", "Hello,Riff"));

        Assertions.assertEquals("Hello,World",
            Strings.defaultIfEmpty("Hello,World", "Hello,Riff"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsEmpty() {
        Assertions.assertTrue(Strings.isEmpty(null));
        Assertions.assertTrue(Strings.isEmpty(""));

        Assertions.assertFalse(Strings.isEmpty("Hello,Riff"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNotEmpty() {
        Assertions.assertFalse(Strings.isNotEmpty(null));
        Assertions.assertFalse(Strings.isNotEmpty(""));

        Assertions.assertTrue(Strings.isNotEmpty("Hello,Riff"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsContains() {
        Assertions.assertTrue(Strings.isContains("Hello,Riff", "Hello"));
        Assertions.assertTrue(Strings.isContains("Hello,Riff", "Riff"));
        Assertions.assertTrue(Strings.isContains("Hello,Riff", ","));

        Assertions.assertFalse(Strings.isContains("Hello,Riff", "World"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNotContains() {
        Assertions.assertFalse(Strings.isNotContains("Hello,Riff", "Hello"));
        Assertions.assertFalse(Strings.isNotContains("Hello,Riff", "Riff"));
        Assertions.assertFalse(Strings.isNotContains("Hello,Riff", ","));

        Assertions.assertTrue(Strings.isNotContains("Hello,Riff", "World"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNumeric() {
        Assertions.assertTrue(Strings.isNumeric("10086"));
        Assertions.assertFalse(Strings.isNumeric("10086x"));
        Assertions.assertFalse(Strings.isNumeric("Abc"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNotNumeric() {
        Assertions.assertFalse(Strings.isNotNumeric("10086"));
        Assertions.assertTrue(Strings.isNotNumeric("10086x"));
        Assertions.assertTrue(Strings.isNotNumeric("Abc"));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNotEquals() {
        Assertions.assertTrue(Strings.isNotEquals("10086", "Abc"));
        Assertions.assertFalse(Strings.isNotEquals("Abc", "Abc"));
    }

    @Test
    void testIsEquals() {
        Assertions.assertFalse(Strings.isEquals("10086", "Abc"));
        Assertions.assertTrue(Strings.isEquals("Abc", "Abc"));
    }

}
