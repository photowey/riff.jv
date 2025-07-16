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

import io.github.photowey.riff.infras.exception.core.domain.body.ExceptionBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@code RethrowGlobalExceptionAdvice}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RethrowGlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ExceptionBody> handleAuthenticationException(AuthenticationException e) {
        throw e;
    }

    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ExceptionBody> handleAccessDeniedException(AccessDeniedException e) {
        throw e;
    }

}
