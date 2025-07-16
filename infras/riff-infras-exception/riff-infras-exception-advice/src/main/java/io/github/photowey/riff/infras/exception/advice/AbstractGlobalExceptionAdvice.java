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
package io.github.photowey.riff.infras.exception.advice;

import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;

import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.exception.core.base.FormattableException;
import io.github.photowey.riff.infras.exception.core.domain.body.ExceptionBody;
import io.github.photowey.riff.infras.exception.core.enums.ExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@code AbstractGlobalExceptionAdvice}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Slf4j
public abstract class AbstractGlobalExceptionAdvice extends AbstractEnvironmentApplyContentType {

    private static final String AUTHORIZATION_ACCOUNT_OR_PASSWORD_INVALID_MESSAGE = "Username or password is incorrect";
    private static final String INPUT_BODY_INVALID_ERROR_MESSAGE = "INVALID_INPUT";

    protected static final UrlPathHelper HELPER = new UrlPathHelper();

    @ResponseBody
    @ExceptionHandler(value = SocketTimeoutException.class)
    public ResponseEntity<ExceptionBody> handleSocketTimeoutException(
        HttpServletRequest request,
        HttpServletResponse response,
        SocketTimeoutException exception) {
        this.populateContentType(response);

        ExceptionBody body = new ExceptionBody(ExceptionStatus.TIMEOUT);
        String profileActivated = this.environment.getProperty(PROFILE_KEY);
        log.error("riff: profile.{} catch socket.timeout exception", profileActivated, exception);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionBody> handleHttpMessageNotReadableException(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpMessageNotReadableException exception) {
        this.populateContentType(response);

        ExceptionBody body = new ExceptionBody(
            ExceptionStatus.BAD_REQUEST,
            INPUT_BODY_INVALID_ERROR_MESSAGE
        );
        String profileActivated = this.environment.getProperty(PROFILE_KEY);
        log.error("riff: catch http.message.not.readable exception, profile:[{}]", profileActivated, exception);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionBody> handleMissingServletRequestParameterException(
        HttpServletRequest request,
        HttpServletResponse response,
        MissingServletRequestParameterException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badRequest();

        String[] profileActivated = this.environment.getActiveProfiles();
        List<String> messages = new ArrayList<>();
        this.handleParameterException(exception, messages);
        this.populateStackMessage(messages, body);

        log.error("riff: catch missing.servlet.request.parameter exception,profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValidException(
        HttpServletRequest request,
        HttpServletResponse response,
        MethodArgumentNotValidException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badRequest();
        String[] profileActivated = this.environment.getActiveProfiles();
        List<String> messages = new ArrayList<>();
        this.tryInjectMethodArgumentNotValidException(exception, messages);
        this.populateStackMessage(messages, body);

        log.error("riff: catch method.argument.not.valid exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ExceptionBody> handleValidationException(
        HttpServletRequest request,
        HttpServletResponse response,
        ValidationException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badRequest();
        String[] profileActivated = this.environment.getActiveProfiles();
        List<String> messages = new ArrayList<>();
        this.tryInjectValidationException(exception, messages);
        this.populateStackMessage(messages, body);

        log.error("riff: catch validation exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionBody> handleBindException(
        HttpServletRequest request,
        HttpServletResponse response,
        BindException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badRequest();
        String[] profileActivated = this.environment.getActiveProfiles();
        List<String> messages = new ArrayList<>();
        this.tryInjectBindException(exception, messages);
        this.populateStackMessage(messages, body);

        log.error("riff: catch Validation exception,profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionBody> handleBadCredentialsException(
        HttpServletRequest request,
        HttpServletResponse response,
        BadCredentialsException exception) {
        this.applyJSONContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.unauthorized(
            AUTHORIZATION_ACCOUNT_OR_PASSWORD_INVALID_MESSAGE
        );
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch bad.credential exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ExceptionBody> handleNullPointerException(
        HttpServletRequest request,
        HttpServletResponse response,
        NullPointerException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.nullPointer();
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch null.pointer exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ExceptionBody> handleIllegalArgumentException(
        HttpServletRequest request,
        HttpServletResponse response,
        IllegalArgumentException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.illegalArgument();
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch illegal.argument exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<ExceptionBody> handleUnsupportedOperationException(
        HttpServletRequest request,
        HttpServletResponse response,
        UnsupportedOperationException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.unsupportedOperation();
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch UnsupportedOperation exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // ----------------------------------------------------------------

    @ResponseBody
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ExceptionBody> handleSQLException(
        HttpServletRequest request,
        HttpServletResponse response,
        SQLException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badUnHandle();
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch SQL exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // ----------------------------------------------------------------

    @ResponseBody
    @ExceptionHandler(value = FormattableException.class)
    public ResponseEntity<ExceptionBody> handleFormattableException(
        HttpServletRequest request,
        HttpServletResponse response,
        FormattableException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);
        String message = exception.getMessage();
        if (Objects.isNotNull(exception.getCause())
            && StringUtils.hasText(exception.getCause().getMessage())) {
            message = exception.getCause().getMessage();
        }

        ExceptionBody body = ExceptionBody.badRequest(message);
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch formatted exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // ----------------------------------------------------------------

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionBody> handleRuntimeException(
        HttpServletRequest request,
        HttpServletResponse response,
        RuntimeException exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);
        String message = exception.getMessage();
        if (Objects.nonNull(exception.getCause())
            && StringUtils.hasText(exception.getCause().getMessage())) {
            message = exception.getCause().getMessage();
        }

        ExceptionBody body = ExceptionBody.runtime(message);
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch Runtime exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ExceptionBody> handleAnonymousThrowable(
        HttpServletRequest request,
        HttpServletResponse response,
        Throwable exception) {
        this.populateContentType(response);
        String method = request.getMethod().toUpperCase();
        String path = HELPER.getLookupPathForRequest(request);

        ExceptionBody body = ExceptionBody.badUnHandle();
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch Unknown exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // ----------------------------------------------------------------

    protected void populateContentType(HttpServletResponse response) {
        this.applyJSONContentType(response);
    }

    // ----------------------------------------------------------------

    private void tryInjectBindException(BindException exception, List<String> messages) {
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError error : bindingResult.getFieldErrors()) {
            messages.add(error.getDefaultMessage());
        }
    }

    private void tryInjectMethodArgumentNotValidException(
        MethodArgumentNotValidException exception, List<String> messages) {
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError error : bindingResult.getFieldErrors()) {
            messages.add(error.getDefaultMessage());
        }
    }

    private void tryInjectValidationException(
        ValidationException exception, List<String> messages) {
        String message = null != exception.getCause() ? exception.getCause().getMessage() : "";
        if (StringUtils.hasLength(message)) {
            messages.add(message);
        }
    }

    private void populateStackMessage(List<String> messages, ExceptionBody body) {
        if (null != messages && !messages.isEmpty()) {
            body.setMessage(String.join(",", messages));
        }
    }

    private void handleParameterException(
        MissingServletRequestParameterException exception, List<String> messages) {
        messages.add("Invalid parameter: " + exception.getParameterName());
    }
}
