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
package io.github.photowey.riff.infras.authentication.jjwt.engine;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.photowey.riff.infras.authentication.api.determiner.IgnorePathDeterminer;
import io.github.photowey.riff.infras.authentication.api.determiner.RequestDeterminer;
import io.github.photowey.riff.infras.authentication.api.encryptor.SubjectEncryptor;
import io.github.photowey.riff.infras.authentication.api.enhancer.IgnorePathEnhancer;
import io.github.photowey.riff.infras.authentication.api.handler.guard.RequestGuardHandler;
import io.github.photowey.riff.infras.authentication.api.handler.password.PasswordHandler;
import io.github.photowey.riff.infras.authentication.api.token.TokenService;
import io.github.photowey.riff.infras.authentication.jjwt.token.JwtTokenService;
import io.github.photowey.riff.infras.authentication.property.getter.SecurityPropertiesGetter;
import io.github.photowey.riff.infras.ioc.context.engine.Engine;

/**
 * {@code AuthenticationEngine}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public interface AuthenticationEngine extends SecurityPropertiesGetter, Engine {

    /**
     * Acquire {@link SubjectEncryptor} instance.
     *
     * @return the {@link SubjectEncryptor} instance.
     */
    default SubjectEncryptor subjectEncryptor() {
        return this.beanFactory().getBean(SubjectEncryptor.class);
    }

    /**
     * Acquire {@link TokenService} instance.
     *
     * @return the {@link TokenService} instance.
     */
    default TokenService tokenService() {
        return this.jwt();
    }

    /**
     * Acquire {@link JwtTokenService} instance.
     *
     * @return the {@link JwtTokenService} instance.
     */
    default JwtTokenService jwt() {
        return this.beanFactory().getBean(JwtTokenService.class);
    }

    /**
     * Acquire {@link PasswordEncoder} instance.
     *
     * @return the {@link PasswordEncoder} instance.
     */
    default PasswordEncoder passwordEncoder() {
        return this.beanFactory().getBean(PasswordEncoder.class);
    }

    /**
     * Acquire {@link PasswordEncoder} instance.
     *
     * @return the {@link PasswordEncoder} instance.
     */
    default PasswordEncoder password() {
        return this.passwordEncoder();
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link RequestGuardHandler} instance.
     *
     * @return the {@link RequestGuardHandler} instance.
     */
    default RequestGuardHandler guard() {
        return this.requestGuardHandler();
    }

    /**
     * Acquire {@link RequestGuardHandler} instance.
     *
     * @return the {@link RequestGuardHandler} instance.
     */
    default RequestGuardHandler requestGuardHandler() {
        return this.beanFactory().getBean(RequestGuardHandler.class);
    }

    /**
     * Acquire {@link RequestDeterminer} instance.
     *
     * @return the {@link RequestDeterminer} instance.
     */
    default RequestDeterminer requestDeterminer() {
        return this.beanFactory().getBean(RequestDeterminer.class);
    }

    /**
     * Acquire {@link IgnorePathEnhancer} instance.
     *
     * @return the {@link IgnorePathEnhancer} instance.
     */
    default IgnorePathEnhancer ignorePathEnhancer() {
        return this.beanFactory().getBean(IgnorePathEnhancer.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link PasswordHandler} instance.
     *
     * @return the {@link PasswordHandler} instance.
     */
    default PasswordHandler passwordHandler() {
        return this.beanFactory().getBean(PasswordHandler.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link AuthenticationManager} instance.
     *
     * @return the {@link AuthenticationManager} instance.
     */
    default AuthenticationManager authenticationManager() {
        return this.beanFactory().getBean(AuthenticationManager.class);
    }

    /**
     * Acquire {@link AuthenticationManager} instance.
     *
     * @return the {@link AuthenticationManager} instance.
     */
    default AuthenticationManager manager() {
        return this.authenticationManager();
    }

    // ---------------------------------------------------------------

    /**
     * Acquire {@link IgnorePathDeterminer} instance.
     *
     * @return the {@link IgnorePathDeterminer} instance.
     */
    default IgnorePathDeterminer ignorePathDeterminer() {
        return this.beanFactory().getBean(IgnorePathDeterminer.class);
    }
}

