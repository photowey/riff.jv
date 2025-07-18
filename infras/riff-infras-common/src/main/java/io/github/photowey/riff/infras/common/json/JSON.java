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
package io.github.photowey.riff.infras.common.json;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.github.photowey.riff.infras.common.exception.Exceptions;
import io.github.photowey.riff.infras.common.util.Objects;

/**
 * {@code JSON}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
@SuppressWarnings("all")
public final class JSON {

    private static ObjectMapper json;
    private static ObjectMapper xml;
    private static ObjectMapper underline;

    // ----------------------------------------------------------------

    public static void json(@Nonnull ObjectMapper json) {
        checkNPE(json);
        JSON.json = json;
    }

    public static ObjectMapper json() {
        checkNPE(json);
        return json;
    }

    public static void xml(@Nonnull ObjectMapper xml) {
        checkNPE(xml);
        JSON.xml = xml;
    }

    public static ObjectMapper xml() {
        checkNPE(xml);
        return xml;
    }

    public static void underline(@Nonnull ObjectMapper underline) {
        checkNPE(underline);
        JSON.underline = underline;
    }

    public static ObjectMapper underline() {
        checkNPE(underline);
        return underline;
    }

    // ----------------------------------------------------------------

    public static String toPrettyString(@Nonnull ObjectMapper objectMapper, @Nonnull String json) {
        checkNPE(objectMapper);
        try {
            // @formatter:off
            return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(objectMapper.readValue(json, JsonNode.class));
            // @formatter:on
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e, String.class);
        }
    }

    public static String toPrettyString(@Nonnull String json) {
        return toPrettyString(json(), json);
    }

    public static String toPrettyXMLString(@Nonnull String xml) {
        return toPrettyString(xml(), xml);
    }

    public static <T> String toBodyPrettyString(T body) {
        return toPrettyString(toJSONString(body));
    }

    public static <T> String toBodyPrettyXMLString(T body) {
        return toPrettyString(toXMLString(body));
    }

    // ----------------------------------------------------------------

    public static <T> String toJSONString(@Nullable T body) {
        if (Objects.isNull(body)) {
            return null;
        }
        try {
            return toJSONString(json(), body);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> String toJSONString(@Nonnull ObjectMapper objectMapper, T body) {
        if (Objects.isNull(body)) {
            return null;
        }

        checkNPE(objectMapper);
        try {
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <K, V> Map<K, V> toMap(@Nonnull String json) {
        try {
            // @formatter:off
            return json().readValue(json, new TypeReference<Map<K, V>>() { });
            // @formatter:on
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <K, V> Map<K, V> toMap(@Nonnull ObjectMapper objectMapper, @Nonnull String kv) {
        checkNPE(objectMapper);
        try {
            // @formatter:off
            return objectMapper.readValue(kv, new TypeReference<Map<K, V>>() { });
            // @formatter:on
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(@Nonnull String json, @Nonnull Class<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(@Nonnull String json, @Nonnull TypeReference<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(@Nonnull ObjectMapper objectMapper, @Nonnull String json, @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseObject(
        @Nonnull ObjectMapper objectMapper,
        @Nonnull String json,
        TypeReference<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(@Nonnull byte[] json, @Nonnull Class<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(@Nonnull byte[] json, @Nonnull TypeReference<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(
        @Nonnull ObjectMapper objectMapper, @Nonnull byte[] json, @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseObject(
        @Nonnull ObjectMapper objectMapper,
        @Nonnull byte[] json,
        TypeReference<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseObject(@Nonnull InputStream json, @Nonnull Class<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(@Nonnull InputStream json, @Nonnull TypeReference<T> clazz) {
        return parseObject(json(), json, clazz);
    }

    public static <T> T parseObject(
        @Nonnull ObjectMapper objectMapper, @Nonnull InputStream json, @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseObject(
        ObjectMapper objectMapper,
        @Nonnull InputStream json,
        TypeReference<T> clazz) {
        checkNPE(objectMapper);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseArray(@Nonnull String json, @Nonnull Class<T> clazz) {
        return parseArray(json(), json, clazz);
    }

    public static <T> List<T> parseArray(
        @Nonnull ObjectMapper objectMapper, @Nonnull String json, @Nonnull Class<T> clazz) {
        return parseList(objectMapper, json, clazz);
    }

    public static <T> List<T> parseArray(
        @Nonnull ObjectMapper objectMapper, @Nonnull byte[] json, @Nonnull Class<T> clazz) {
        return parseList(objectMapper, json, clazz);
    }

    public static <T> List<T> parseArray(
        ObjectMapper objectMapper, @Nonnull InputStream json, @Nonnull Class<T> clazz) {
        return parseList(objectMapper, json, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> String toXMLString(T body) {
        try {
            return xml().writeValueAsString(body);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseXMLObject(@Nonnull String xml, @Nonnull Class<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseXMLObject(@Nonnull String xml, @Nonnull TypeReference<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> T parseXMLObject(@Nonnull byte[] xml, @Nonnull Class<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseXMLObject(@Nonnull byte[] xml, @Nonnull TypeReference<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseXMLObject(@Nonnull InputStream xml, @Nonnull Class<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> T parseXMLObject(@Nonnull InputStream xml, @Nonnull TypeReference<T> clazz) {
        try {
            return xml().readValue(xml, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseXMLArray(@Nonnull String xml, @Nonnull Class<T> clazz) {
        return parseXMLArray(xml(), xml, clazz);
    }

    public static <T> List<T> parseXMLArray(
        @Nonnull ObjectMapper xmlObjectMapper,
        @Nonnull String xml,
        @Nonnull Class<T> clazz) {
        return parseList(xmlObjectMapper, xml, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> List<T> parseList(@Nonnull String json, @Nonnull Class<T> clazz) {
        try {
            return parseList(json(), json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(@Nonnull byte[] json, @Nonnull Class<T> clazz) {
        try {
            return parseList(json(), json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(InputStream json, @Nonnull Class<T> clazz) {
        try {
            return parseList(json(), json, clazz);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(
        @Nonnull ObjectMapper objectMapper, @Nonnull String json, @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            CollectionType collectionType = toListCollectionType(objectMapper, clazz);
            return objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(ObjectMapper objectMapper, @Nonnull byte[] json, @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            CollectionType collectionType = toListCollectionType(objectMapper, clazz);
            return objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    public static <T> List<T> parseList(
        @Nonnull ObjectMapper objectMapper,
        @Nonnull InputStream json,
        @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        try {
            CollectionType collectionType = toListCollectionType(objectMapper, clazz);
            return objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            return Exceptions.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    public static <T> String toUnderlineString(T body) {
        return toJSONString(underline(), body);
    }

    public static <T> T parseUnderlineObject(@Nonnull String json, @Nonnull Class<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> T parseUnderlineObject(@Nonnull String json, @Nonnull TypeReference<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> List<T> parseUnderlineArray(@Nonnull String json, @Nonnull Class<T> clazz) {
        return parseArray(underline(), json, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> T parseUnderlineObject(@Nonnull byte[] json, @Nonnull Class<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> T parseUnderlineObject(@Nonnull byte[] json, @Nonnull TypeReference<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> List<T> parseUnderlineArray(@Nonnull byte[] json, @Nonnull Class<T> clazz) {
        return parseArray(underline(), json, clazz);
    }

    // ----------------------------------------------------------------

    public static <T> T parseUnderlineObject(@Nonnull InputStream json, @Nonnull Class<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> T parseUnderlineObject(@Nonnull InputStream json, @Nonnull TypeReference<T> clazz) {
        return parseObject(underline(), json, clazz);
    }

    public static <T> List<T> parseUnderlineArray(@Nonnull InputStream json, @Nonnull Class<T> clazz) {
        return parseArray(underline(), json, clazz);
    }

    // ----------------------------------------------------------------

    public static boolean predicateIsJSONString(@Nonnull String json) {
        try {
            json().readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    public static boolean predicateIsXMLString(@Nonnull String json) {
        try {
            xml().readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    // ----------------------------------------------------------------

    private static <T> CollectionType toListCollectionType(
        @Nonnull ObjectMapper objectMapper,
        @Nonnull Class<T> clazz) {
        checkNPE(objectMapper);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return typeFactory.constructCollectionType(List.class, clazz);
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    private static void checkNPE(@Nullable ObjectMapper objectMapper) {
        Exceptions.checkNPE(objectMapper, "riff: the objectMapper can't be null.");
    }
}
