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
package io.github.photowey.riff.business.uaa.web.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.photowey.riff.infras.authentication.core.exception.SecurityAuthenticationException;
import io.github.photowey.riff.infras.crypto.core.exception.CryptoException;
import io.github.photowey.riff.infras.exception.advice.AbstractGlobalExceptionAdvice;
import io.github.photowey.riff.infras.exception.core.domain.body.ExceptionBody;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code UaaGlobalExceptionAdvice}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
@ControllerAdvice
@Order(101)
public class UaaGlobalExceptionAdvice extends AbstractGlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = CryptoException.class)
    public ResponseEntity<ExceptionBody> handleCryptoException(
        HttpServletRequest request,
        HttpServletResponse response,
        CryptoException exception) {
        this.populateContentType(response);
        String method = this.determineRequestMethod(request);
        String path = this.determineRequestPath(request);
        String message = this.determineExceptionMessage(exception);

        ExceptionBody body = ExceptionBody.badRequest(message);
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch crypto exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(value = SecurityAuthenticationException.class)
    public ResponseEntity<ExceptionBody> handleSecurityAuthenticationException(
        HttpServletRequest request,
        HttpServletResponse response,
        SecurityAuthenticationException exception) {
        this.populateContentType(response);
        String method = this.determineRequestMethod(request);
        String path = this.determineRequestPath(request);
        String message = this.determineExceptionMessage(exception);

        ExceptionBody body = ExceptionBody.badRequest(message);
        String[] profileActivated = this.environment.getActiveProfiles();
        log.error("riff: catch security.authentication exception, profile:[{}] [{} {}]",
            this.arrayToString(profileActivated), method, path, exception
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
