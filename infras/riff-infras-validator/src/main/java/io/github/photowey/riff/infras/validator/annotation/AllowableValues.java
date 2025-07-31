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
package io.github.photowey.riff.infras.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import io.github.photowey.riff.infras.validator.processor.AllowableValuesValidatorAnnotationProcessor;

/**
 * {@code AllowableValues}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/31
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = AllowableValuesValidatorAnnotationProcessor.class)
public @interface AllowableValues {

    String value() default "";

    boolean required() default true;

    int size() default Integer.MAX_VALUE;

    String message() default "enter the correct parameters,please!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

