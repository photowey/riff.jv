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
package io.github.photowey.riff.infras.validator.processor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidatorContext;

import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.validator.annotation.AllowableValues;

/**
 * {@code AllowableValuesValidatorAnnotationProcessor}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/31
 */
public class AllowableValuesValidatorAnnotationProcessor
    extends AbstractAnnotationProcessorAdaptor<AllowableValues, Object> {

    private String allowableValues;
    private String trimmed;
    private boolean required;
    private int size;

    private static final String RANGE_SYMBOL = "range";
    private static final String INFINITY_SYMBOL = "infinity";
    private static final String NEGATIVE_INFINITY_SYMBOL = "range(-infinity";

    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";
    private static final String COMMA = ",";
    private static final String HYPHEN = "-";

    private static final Pattern RANGE_DOUBLE_PATTERN =
        Pattern.compile("^range[\\\\(\\[]?(\\d+)-(\\d+)[\\\\)|\\]]?");
    private static final Pattern RANGE_NEGATIVE_PATTERN =
        Pattern.compile("^range\\(-infinity-(\\d+)[\\\\)\\]]");
    private static final Pattern RANGE_POSITIVE_PATTERN =
        Pattern.compile("^range[\\\\(\\[]?(\\d+)-infinity[\\\\)\\]]");
    private static final Pattern RANGE_SINGLE_PATTERN =
        Pattern.compile("^range[\\\\(\\[]?(\\d+)[\\\\)\\]]");

    @Override
    public void initialize(AllowableValues constraintAnnotation) {
        this.allowableValues = constraintAnnotation.value();
        this.trimmed = this.allowableValues.trim();
        this.required = constraintAnnotation.required();
        this.size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return !this.required;
        }

        if (value instanceof Collection<?> values) {
            if (values.size() > this.size) {
                return false;
            }

            return this.handleCollection(values);
        }

        return this.handleSingle(value);
    }

    private boolean handleSingle(Object value) {
        String expectValue = String.valueOf(value);
        if (this.trimmed.startsWith(RANGE_SYMBOL)) {
            return this.handleRange(expectValue);
        }

        return this.handleNormal(value, expectValue);
    }

    private boolean handleCollection(Collection<?> values) {
        for (Object it : values) {
            if (!(it instanceof Number)) {
                throw new IllegalArgumentException(
                    "@AllowableValues: Range validation only supports numeric values. Invalid input: " + it);
            }
            if (!this.handleSingle(it)) {
                return false;
            }
        }

        return true;
    }

    private boolean handleNormal(Object value, String input) {
        if (this.trimmed.contains(HYPHEN)) {
            throw new IllegalArgumentException("@AllowableValues: "
                + "Hyphen '-' is not allowed in non-range value lists. Use 'range[min-max]' for ranges.");
        }
        if (this.trimmed.contains(COMMA)) {
            // xxx,yyy,zzz
            return Stream.of(this.trimmed.split(COMMA))
                .collect(Collectors.toSet())
                .contains(input);
        }

        // xxx
        return value instanceof BigDecimal
            ? new BigDecimal(this.trimmed).compareTo(new BigDecimal(input)) == 0
            : this.trimmed.equalsIgnoreCase(input);
    }

    private boolean handleRange(String input) {
        if (Strings.isNotNumeric(input)) {
            throw new IllegalArgumentException(
                "@AllowableValues: Range validation only supports numeric values. Invalid input: " + input);
        }

        BigDecimal numberValue = new BigDecimal(input);
        if (this.trimmed.contains(NEGATIVE_INFINITY_SYMBOL)) {
            return this.validateNegativeInfinityRange(numberValue);
        }

        if (this.trimmed.contains(INFINITY_SYMBOL)) {
            return this.validatePositiveInfinityRange(numberValue);
        }

        if (this.trimmed.contains(HYPHEN)) {
            return this.validateBoundedRange(numberValue);
        }

        return this.validateSingleValueRange(input);
    }

    private boolean validateBoundedRange(BigDecimal numberValue) {
        // range[xxx-yyy] || range(xxx-yyy] || range[xxx-yyy) || range(xxx-yyy)
        Matcher matcher = RANGE_DOUBLE_PATTERN.matcher(this.trimmed);

        BigDecimal lowerBound = new BigDecimal(matcher.group(1));
        BigDecimal upperBound = new BigDecimal(matcher.group(2));

        int lowerComparison = numberValue.compareTo(lowerBound);
        int upperComparison = numberValue.compareTo(upperBound);

        boolean lowerValid = this.trimmed.contains(LEFT_SQUARE_BRACKET) ? lowerComparison >= 0 : lowerComparison > 0;
        boolean upperValid = this.trimmed.contains(RIGHT_SQUARE_BRACKET) ? upperComparison <= 0 : upperComparison < 0;

        // xxx-yyy
        return lowerValid && upperValid;
    }

    private boolean validatePositiveInfinityRange(BigDecimal numberValue) {
        // range[xxx-infinity) || range(xxx-infinity)
        // -> xxx
        Matcher matcher = RANGE_POSITIVE_PATTERN.matcher(this.trimmed);

        BigDecimal lowerBound = new BigDecimal(matcher.group(1));
        int comparison = numberValue.compareTo(lowerBound);

        // Check if left bracket is inclusive [ or exclusive (
        return this.trimmed.contains(LEFT_BRACKET)
            ? comparison >= 0
            : comparison > 0;
    }

    private boolean validateNegativeInfinityRange(BigDecimal numberValue) {
        // range(-infinity-xxx] || range(-infinity-xxx)
        Matcher matcher = RANGE_NEGATIVE_PATTERN.matcher(this.trimmed);

        BigDecimal upperBound = new BigDecimal(matcher.group(1));
        int comparison = numberValue.compareTo(upperBound);

        // xxx
        return trimmed.contains(RIGHT_SQUARE_BRACKET)
            ? comparison <= 0
            : comparison < 0;
    }

    private boolean validateSingleValueRange(String input) {
        if (this.trimmed.contains(LEFT_BRACKET) || this.trimmed.contains(RIGHT_BRACKET)) {
            throw new IllegalArgumentException("@AllowableValues: "
                + "Invalid syntax for single-value range. Use 'range[value]', not 'range[value1,value2]'.");
        }

        Matcher matcher = RANGE_SINGLE_PATTERN.matcher(this.trimmed);
        return new BigDecimal(input).compareTo(new BigDecimal(matcher.group(1))) == 0;
    }
}
