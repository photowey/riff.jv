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

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ObjectsTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/14
 */
class ObjectsTest {

    @Test
    void testDefaultIfNull() {
        Integer target = 10086;
        Integer notNull = Objects.defaultIfNull(target, 1);
        Assertions.assertEquals(10086, notNull);

        Integer defaultIfNull = Objects.defaultIfNull(null, 1);
        Assertions.assertEquals(1, defaultIfNull);
    }

    @Test
    void testDefaultIfNull_Supplier() {
        Integer target = 10086;
        Integer notNull = Objects.tryDefaultIfNull(target, () -> 1);
        Assertions.assertEquals(10086, notNull);

        Integer defaultIfNull = Objects.tryDefaultIfNull(null, () -> 1);
        Assertions.assertEquals(1, defaultIfNull);
    }

    // ----------------------------------------------------------------

    @Test
    void testDefaultIfEmpty() {
        List<Integer> defaultIfEmpty = Objects.defaultIfEmpty(
            Arrays.asImmutableList(), Arrays.asImmutableList(1, 2));

        Assertions.assertEquals(2, defaultIfEmpty.size());
        Assertions.assertTrue(defaultIfEmpty.contains(1));
        Assertions.assertTrue(defaultIfEmpty.contains(2));
        Assertions.assertFalse(defaultIfEmpty.contains(3));
    }

    @Test
    void testDefaultIfEmpty_Supplier() {
        List<Integer> asImmutableList = Arrays.asImmutableList();
        List<Integer> defaultIfEmpty = Objects.tryDefaultIfEmpty(asImmutableList, () -> {
            return Arrays.asImmutableList(1, 2);
        });

        Assertions.assertEquals(2, defaultIfEmpty.size());
        Assertions.assertTrue(defaultIfEmpty.contains(1));
        Assertions.assertTrue(defaultIfEmpty.contains(2));
        Assertions.assertFalse(defaultIfEmpty.contains(3));
    }

    // ----------------------------------------------------------------

    @Test
    void testIsNull() {
        Assertions.assertTrue(Objects.isNull(null));
        Assertions.assertFalse(Objects.isNull(1));
    }

    @Test
    void testIsNotNull() {
        Assertions.assertFalse(Objects.isNotNull(null));
        Assertions.assertTrue(Objects.isNotNull(1));
    }

    // ----------------------------------------------------------------

    @Test
    void testRequireNonNull() {
        Assertions.assertEquals(1, Objects.requireNonNull(1));
        Assertions.assertThrows(NullPointerException.class, () -> {
            Objects.requireNonNull(null);
        });
    }

    // ----------------------------------------------------------------

    @Test
    void testNotEquals() {
        Assertions.assertTrue(Objects.notEquals(1, 2));
        Assertions.assertFalse(Objects.notEquals(1, 1));
    }

    @Test
    void testEquals() {
        Assertions.assertFalse(Objects.equals(1, 2));
        Assertions.assertTrue(Objects.equals(1, 1));
    }

    @Test
    void testDeepEquals() {
        Hello hello1 = new Hello(5_000L, 20, "Tom");
        Hello hello2 = new Hello(5_000L, 20, "Tom");

        Assertions.assertTrue(Objects.equals(hello1, hello2));
        Assertions.assertFalse(Objects.notEquals(hello1, hello2));
        Assertions.assertTrue(Objects.deepEquals(hello1, hello2));

        Assertions.assertTrue(
            Objects.deepEquals(Arrays.asImmutableList(1, 2), Arrays.asImmutableList(1, 2)));

        Assertions.assertTrue(
            Objects.deepEquals(Arrays.asImmutableList(hello1), Arrays.asImmutableList(hello2)));
    }

    @Test
    void testDeepEquals_2() {
        World world1 = new World(5_000L, 20, "Tom");
        World world2 = new World(5_000L, 20, "Tom");

        Assertions.assertFalse(Objects.equals(world1, world2));
        Assertions.assertTrue(Objects.notEquals(world1, world2));
        Assertions.assertFalse(Objects.deepEquals(world1, world2));
        Assertions.assertFalse(
            Objects.deepEquals(Arrays.asImmutableList(world1), Arrays.asImmutableList(world2)));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private final static class Hello implements Serializable {

        @Serial
        private static final long serialVersionUID = -8674474676701908932L;

        private Long id;
        private Integer age;
        private String name;
    }

    private final static class World implements Serializable {

        @Serial
        private static final long serialVersionUID = -8674474676701908932L;

        private Long id;
        private Integer age;
        private String name;

        private World(Long id, Integer age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
