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
package io.github.photowey.riff.business.uaa.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.github.photowey.riff.business.uaa.security.checker.AuthenticationAccountChecker;
import io.github.photowey.riff.business.uaa.security.checker.DefaultAuthenticationAccountChecker;
import io.github.photowey.riff.business.uaa.security.filter.JwtAuthenticationFilter;
import io.github.photowey.riff.business.uaa.security.handler.authentication.AuthenticatedHandler;
import io.github.photowey.riff.business.uaa.security.handler.authentication.DefaultAuthenticatedHandler;
import io.github.photowey.riff.business.uaa.security.loader.AccountAuthenticationUserDetailsLoader;
import io.github.photowey.riff.business.uaa.security.loader.OauthClientAuthenticationUserDetailsLoader;
import io.github.photowey.riff.business.uaa.security.service.SpringSecurityService;
import io.github.photowey.riff.business.uaa.security.service.impl.DomainUserDetailsServiceImpl;
import io.github.photowey.riff.business.uaa.security.service.impl.SpringSecurityServiceImpl;
import io.github.photowey.riff.infras.authentication.core.constant.AntPathConstants;
import io.github.photowey.riff.infras.authentication.jjwt.engine.AuthenticationEngine;

/**
 * {@code UaaAutoConfiguration}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@AutoConfiguration
@Import(value = {
    UaaAutoConfiguration.UaaAutoComponentConfiguration.class,
    UaaAutoConfiguration.UaaUserDetailsLoaderConfiguration.class,
})
public class UaaAutoConfiguration {

    @Configuration
    public static class UaaAutoComponentConfiguration {

        @Bean
        public UserDetailsService userDetailsService() {
            return new DomainUserDetailsServiceImpl();
        }

        /**
         * 获取 {@code SpringSecurityService} 实例
         * y3s: YzCloud SpringSecurityService
         *
         * @return {@link SpringSecurityService}
         */
        @Bean("y3s")
        public SpringSecurityService springSecurityService() {
            return new SpringSecurityServiceImpl();
        }

        @Bean
        public AuthenticatedHandler authenticatedHandler(
            AuthenticationEngine authenticationEngine) {
            return new DefaultAuthenticatedHandler(authenticationEngine);
        }

        @Bean
        public AuthenticationAccountChecker authenticationAccountChecker() {
            return new DefaultAuthenticationAccountChecker();
        }

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationEngine authenticationEngine) {
            return new JwtAuthenticationFilter(authenticationEngine);
        }

        @Bean
        public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationRegistrationBean(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
            FilterRegistrationBean<JwtAuthenticationFilter> registry =
                new FilterRegistrationBean<>();
            registry.setFilter(jwtAuthenticationFilter);
            registry.addUrlPatterns(AntPathConstants.PATH_PATTERN_ALL);
            registry.setOrder(0);

            return registry;
        }
    }

    @Configuration
    public static class UaaUserDetailsLoaderConfiguration {

        @Bean
        public AccountAuthenticationUserDetailsLoader accountAuthenticationUserDetailsLoader() {
            return new AccountAuthenticationUserDetailsLoader();
        }

        @Bean
        public OauthClientAuthenticationUserDetailsLoader oauthClientAuthenticationUserDetailsLoader() {
            return new OauthClientAuthenticationUserDetailsLoader();
        }
    }
}
