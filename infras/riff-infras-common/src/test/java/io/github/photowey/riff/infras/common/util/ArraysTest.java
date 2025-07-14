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

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code ArraysTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
@SuppressWarnings("all")
class ArraysTest {

    @Test
    void testIsEmpty_Object_T() {
        Object[] haystack = new Object[0];
        boolean empty = Arrays.isEmpty(haystack);
        Assertions.assertTrue(empty);

        Object[] haystackNull = null;
        boolean emptyNull = Arrays.isEmpty(haystackNull);
        Assertions.assertTrue(emptyNull);

        Object[] haystackNotNull = new Object[] {
            "Hello, Riff",
        };
        boolean emptyNotNull = Arrays.isEmpty(haystackNotNull);
        Assertions.assertFalse(emptyNotNull);
    }

    @Test
    void testIsEmpty_byte() {
        byte[] haystack = new byte[0];
        boolean empty = Arrays.isEmpty(haystack);
        Assertions.assertTrue(empty);

        byte[] haystackNull = null;
        boolean emptyNull = Arrays.isEmpty(haystackNull);
        Assertions.assertTrue(emptyNull);

        byte[] haystackNotNull = new byte[] {
            (byte) 1
        };
        boolean emptyNotNull = Arrays.isEmpty(haystackNotNull);
        Assertions.assertFalse(emptyNotNull);
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNotEmpty_Object_T() {
        Object[] haystack = new Object[0];
        boolean empty = Arrays.isNotEmpty(haystack);
        Assertions.assertFalse(empty);

        Object[] haystackNull = null;
        boolean emptyNull = Arrays.isNotEmpty(haystackNull);
        Assertions.assertFalse(emptyNull);

        Object[] haystackNotNull = new Object[] {
            "Hello, Riff",
        };
        boolean emptyNotNull = Arrays.isNotEmpty(haystackNotNull);
        Assertions.assertTrue(emptyNotNull);
    }

    @Test
    void testIsNotEmpty_byte() {
        byte[] haystack = new byte[0];
        boolean notEmpty = Arrays.isNotEmpty(haystack);
        Assertions.assertFalse(notEmpty);

        byte[] haystackNull = null;
        boolean notEmptyNull = Arrays.isNotEmpty(haystackNull);
        Assertions.assertFalse(notEmptyNull);

        byte[] haystackNotNull = new byte[] {
            (byte) 1
        };
        boolean notEmptyNotNull = Arrays.isNotEmpty(haystackNotNull);
        Assertions.assertTrue(notEmptyNotNull);
    }

    // ----------------------------------------------------------------

    @Test
    void testAsMutableList() {
        List<Integer> mutableList = Arrays.asMutableList(1, 2);
        Assertions.assertEquals(2, mutableList.size());

        mutableList.add(3);
        Assertions.assertEquals(3, mutableList.size());
    }

    @Test
    void testAsImmutableList() {
        List<Integer> immutableList = Arrays.asImmutableList(1, 2);
        Assertions.assertEquals(2, immutableList.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableList.add(3);
        });
    }

    @Test
    void testAsList() {
        List<Integer> asList = Arrays.asList(1, 2);
        Assertions.assertEquals(2, asList.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            asList.add(3);
        });
    }

    // ----------------------------------------------------------------

    @Test
    void testAsMutableSet() {
        Set<Integer> mutableSet = Arrays.asMutableSet(1, 2);
        Assertions.assertEquals(2, mutableSet.size());

        mutableSet.add(3);
        Assertions.assertEquals(3, mutableSet.size());
    }

    @Test
    void testAsImmutableSet() {
        Set<Integer> immutableSet = Arrays.asImmutableSet(1, 2);
        Assertions.assertEquals(2, immutableSet.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableSet.add(3);
        });
    }

    @Test
    void testAsSet() {
        Set<Integer> asSet = Arrays.asSet(1, 2);
        Assertions.assertEquals(2, asSet.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            asSet.add(3);
        });
    }
}
