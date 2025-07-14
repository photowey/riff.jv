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
 * {@code CollectionsTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
class CollectionsTest {

    @Test
    void testIsEmpty() {
        List<Integer> emptyList = Collections.emptyList();
        Assertions.assertTrue(Collections.isEmpty(emptyList));
        Assertions.assertTrue(Collections.isEmpty(null));

        List<Integer> immutableList = Arrays.asImmutableList(1, 2);
        boolean empty = Collections.isEmpty(immutableList);
        Assertions.assertFalse(empty);
    }

    @Test
    void testIsNotEmpty() {
        List<Integer> emptyList = Collections.emptyList();
        Assertions.assertFalse(Collections.isNotEmpty(emptyList));
        Assertions.assertFalse(Collections.isNotEmpty(null));

        List<Integer> immutableList = Arrays.asImmutableList(1, 2);
        boolean notEmpty = Collections.isNotEmpty(immutableList);
        Assertions.assertTrue(notEmpty);
    }

    // ----------------------------------------------------------------

    @Test
    void testIsContains() {
        List<Integer> immutableList = Arrays.asImmutableList(1, 2);

        Assertions.assertTrue(Collections.isContains(immutableList, 1));
        Assertions.assertTrue(Collections.isContains(immutableList, 2));
        Assertions.assertFalse(Collections.isContains(immutableList, 3));
    }

    @Test
    void testIsNotContains() {
        List<Integer> immutableList = Arrays.asImmutableList(1, 2);

        Assertions.assertFalse(Collections.isNotContains(immutableList, 1));
        Assertions.assertFalse(Collections.isNotContains(immutableList, 2));
        Assertions.assertTrue(Collections.isNotContains(immutableList, 3));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsSingle() {
        Assertions.assertFalse(Collections.isSingle(Arrays.asImmutableList()));
        Assertions.assertTrue(Collections.isSingle(Arrays.asImmutableList(1)));
        Assertions.assertFalse(Collections.isSingle(Arrays.asImmutableList(1, 2)));
    }

    // ----------------------------------------------------------------

    @Test
    void testNewArrayList() {
        List<Integer> newArrayList = Collections.newArrayList(1, 2, 3);
        Assertions.assertEquals(3, newArrayList.size());

        newArrayList.add(4);
        Assertions.assertEquals(4, newArrayList.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testNewHashSet() {
        Set<Integer> newHashSet = Collections.newHashSet(1, 2, 3);
        Assertions.assertEquals(3, newHashSet.size());

        newHashSet.add(4);
        Assertions.assertEquals(4, newHashSet.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testAsMutableList() {
        List<Integer> asMutableList = Collections.asMutableList(1, 2, 3);
        Assertions.assertEquals(3, asMutableList.size());

        asMutableList.add(4);
        Assertions.assertEquals(4, asMutableList.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testAsImmutableList() {
        List<Integer> asImmutableList = Collections.asImmutableList(1, 2, 3);
        Assertions.assertEquals(3, asImmutableList.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            asImmutableList.add(4);
        });
    }

    // ----------------------------------------------------------------

    @Test
    void testAsMutableSet() {
        Set<Integer> asMutableSet = Collections.asMutableSet(1, 2, 3);
        Assertions.assertEquals(3, asMutableSet.size());

        asMutableSet.add(4);
        Assertions.assertEquals(4, asMutableSet.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testAsImmutableSet() {
        Set<Integer> asImmutableSet = Collections.asImmutableSet(1, 2, 3);
        Assertions.assertEquals(3, asImmutableSet.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            asImmutableSet.add(4);
        });
    }

    // ----------------------------------------------------------------

    @Test
    void testCopyList() {
        List<Integer> asImmutableList = Collections.asImmutableList(1, 2, 3);

        List<Integer> copyList = Collections.copyList(asImmutableList);
        Assertions.assertEquals(3, copyList.size());
        copyList.add(4);
        Assertions.assertEquals(4, copyList.size());
    }

    @Test
    void testCopyList_Array() {
        List<Integer> copyList = Collections.copyList(1, 2, 3);
        Assertions.assertEquals(3, copyList.size());
        copyList.add(4);
        Assertions.assertEquals(4, copyList.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testCopySet() {
        Set<Integer> asImmutableSet = Collections.asImmutableSet(1, 2, 3);

        Set<Integer> copySet = Collections.copySet(asImmutableSet);
        Assertions.assertEquals(3, copySet.size());
        copySet.add(4);
        Assertions.assertEquals(4, copySet.size());
    }

    @Test
    void testCopySet_Array() {
        Set<Integer> copySet = Collections.copySet(1, 2, 3);
        Assertions.assertEquals(3, copySet.size());
        copySet.add(4);
        Assertions.assertEquals(4, copySet.size());
    }

    // ----------------------------------------------------------------

    @Test
    void testImmutableCopyList_Array() {
        List<Integer> immutableCopyList = Collections.immutableCopyList(1, 2, 3);
        Assertions.assertEquals(3, immutableCopyList.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopyList.add(4);
        });
    }

    @Test
    void testImmutableCopyList_List() {
        List<Integer> asImmutableList = Collections.asImmutableList(1, 2, 3);
        List<Integer> immutableCopyList = Collections.immutableCopyList(asImmutableList);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopyList.add(4);
        });
    }

    @Test
    void testImmutableCopyList_Set() {
        Set<Integer> asImmutableSet = Collections.asImmutableSet(1, 2, 3);
        List<Integer> immutableCopyList = Collections.immutableCopyList(asImmutableSet);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopyList.add(4);
        });
    }

    // ----------------------------------------------------------------

    @Test
    void testImmutableCopySet_Array() {
        Set<Integer> immutableCopySet = Collections.immutableCopySet(1, 2, 3);
        Assertions.assertEquals(3, immutableCopySet.size());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopySet.add(4);
        });
    }

    @Test
    void testImmutableCopySet_List() {
        List<Integer> asImmutableList = Collections.asImmutableList(1, 2, 3);
        Set<Integer> immutableCopySet = Collections.immutableCopySet(asImmutableList);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopySet.add(4);
        });
    }

    @Test
    void testImmutableCopySet_Set() {
        Set<Integer> asImmutableSet = Collections.asImmutableSet(1, 2, 3);
        Set<Integer> immutableCopySet = Collections.immutableCopySet(asImmutableSet);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableCopySet.add(4);
        });
    }
}
