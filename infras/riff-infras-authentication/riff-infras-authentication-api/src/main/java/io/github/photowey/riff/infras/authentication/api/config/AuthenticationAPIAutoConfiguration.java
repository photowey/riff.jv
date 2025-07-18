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
package io.github.photowey.riff.infras.authentication.api.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.photowey.riff.infras.authentication.api.determiner.DefaultRequestDeterminer;
import io.github.photowey.riff.infras.authentication.api.determiner.IgnorePathDeterminer;
import io.github.photowey.riff.infras.authentication.api.determiner.RequestDeterminer;
import io.github.photowey.riff.infras.authentication.api.encryptor.DefaultSubjectEncryptor;
import io.github.photowey.riff.infras.authentication.api.encryptor.SubjectEncryptor;
import io.github.photowey.riff.infras.authentication.api.enhancer.DefaultIgnorePathEnhancer;
import io.github.photowey.riff.infras.authentication.api.enhancer.IgnorePathEnhancer;
import io.github.photowey.riff.infras.authentication.api.handler.guard.DefaultRequestGuardHandler;
import io.github.photowey.riff.infras.authentication.api.handler.guard.RequestGuardHandler;
import io.github.photowey.riff.infras.authentication.api.handler.password.DefaultBcryptPasswordHandler;
import io.github.photowey.riff.infras.authentication.api.handler.password.PasswordHandler;
import io.github.photowey.riff.infras.authentication.property.SecurityProperties;
import io.github.photowey.riff.infras.ioc.binder.PropertyBinders;

/**
 * {@code AuthenticationAPIAutoConfiguration}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@AutoConfiguration
public class AuthenticationAPIAutoConfiguration {

    @Bean
    public SecurityProperties securityProperties(Environment environment) {
        String prefix = SecurityProperties.determineSecurityPropertyPrefix();
        return PropertyBinders.bind(environment, prefix, SecurityProperties.class);
    }

    @Bean
    public IgnorePathDeterminer ignorePathDeterminer() {
        return new IgnorePathDeterminer();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestDeterminer requestDeterminer() {
        return new DefaultRequestDeterminer();
    }

    @Bean
    @ConditionalOnMissingBean
    public SubjectEncryptor subjectEncryptor() {
        return new DefaultSubjectEncryptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordHandler passwordHandler(PasswordEncoder passwordEncoder) {
        return new DefaultBcryptPasswordHandler(passwordEncoder);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestGuardHandler requestGuardHandler(IgnorePathDeterminer ignorePathDeterminer) {
        return new DefaultRequestGuardHandler(ignorePathDeterminer);
    }

    @Bean
    @ConditionalOnMissingBean
    public IgnorePathEnhancer ignorePathEnhancer() {
        return new DefaultIgnorePathEnhancer();
    }
}
