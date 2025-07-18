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
package io.github.photowey.riff.infras.ioc.context.validator;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import io.github.photowey.riff.infras.common.util.Collections;

/**
 * {@code DefaultEntityValidator}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public class DefaultEntityValidator implements EntityValidator {

    private final Validator validator;
    private final LocalValidatorFactoryBean factoryBean;

    public DefaultEntityValidator(LocalValidatorFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
        this.validator = factoryBean.getValidator();
    }

    @Override
    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (Collections.isNotEmpty(violations)) {
            String message = parseViolations(violations);

            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public <T> void validate(T object, Consumer<Set<ConstraintViolation<T>>> fx) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (Collections.isNotEmpty(violations)) {
            fx.accept(violations);
        }
    }

    @Override
    public <T> void validate(T object, Supplier<String> fx) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (Collections.isNotEmpty(violations)) {
            throw new IllegalArgumentException(fx.get());
        }
    }

    public static <T> String parseViolations(Set<ConstraintViolation<T>> violations) {
        StringBuilder buf = new StringBuilder();

        for (ConstraintViolation<T> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            buf.append(fieldName).append(":").append(message).append(";");
        }

        return buf.toString().replaceAll(";*$", "");
    }
}
