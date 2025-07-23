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
package io.github.photowey.riff.business.uaa.security.config.security;

import java.util.Optional;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.github.photowey.riff.business.uaa.security.handler.security.JwtAccessDeniedHandler;
import io.github.photowey.riff.business.uaa.security.handler.security.JwtAuthenticationEntryPoint;
import io.github.photowey.riff.infras.authentication.api.determiner.IgnorePathDeterminer;
import io.github.photowey.riff.infras.authentication.core.constant.AntPathConstants;
import io.github.photowey.riff.infras.authentication.property.SecurityProperties;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code SpringSecurityAutoConfiguration}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
@Slf4j
@AutoConfiguration(after = SpringSecurityInitAutoConfiguration.class)
@Import(value = {
    SpringSecurityAutoConfiguration.SpringPropertyConfiguration.class,
    SpringSecurityAutoConfiguration.SpringSecurityConfiguration.class,
})
public class SpringSecurityAutoConfiguration {

    private static final String CORS_ENABLED_PROPERTY_KEY = "io.github.photowey.riff.global.security.cors.enabled";
    private static final String CORS_PATH_PATTERN = "/**";

    @Configuration
    public static class SpringPropertyConfiguration {

        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new JwtAccessDeniedHandler();
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            return new JwtAuthenticationEntryPoint();
        }
    }

    @Configuration
    public static class SpringSecurityConfiguration
        extends AbstractBeanFactoryHolder implements Ordered {

        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;

        private final SecurityProperties securityProperties;
        private final IgnorePathDeterminer ignorePathDeterminer;
        private final AuthenticationManagerBuilder auth;
        private final JwtSecurityConfigurer securityConfigurer;

        private final AccessDeniedHandler accessDeniedHandler;
        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final Environment environment;

        public SpringSecurityConfiguration(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            SecurityProperties securityProperties,
            IgnorePathDeterminer ignorePathDeterminer,
            AuthenticationManagerBuilder auth,
            JwtSecurityConfigurer securityConfigurer,
            AccessDeniedHandler accessDeniedHandler,
            AuthenticationEntryPoint authenticationEntryPoint,
            Environment environment
        ) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.ignorePathDeterminer = ignorePathDeterminer;
            this.securityProperties = securityProperties;
            this.auth = auth;
            this.securityConfigurer = securityConfigurer;
            this.accessDeniedHandler = accessDeniedHandler;
            this.authenticationEntryPoint = authenticationEntryPoint;
            this.environment = environment;
        }

        @Override
        public int getOrder() {
            // 100 WebSecurityConfiguration
            return 100 << 1;
        }

        @PostConstruct
        protected void configure() throws Exception {
            this.auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
        }

        @Bean
        @ConditionalOnProperty(
            name = CORS_ENABLED_PROPERTY_KEY,
            havingValue = "true",
            matchIfMissing = true
        )
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = Optional.ofNullable(
                this.securityProperties.getCors()).orElseGet(CorsConfiguration::new);

            if (isCorsConfigValid(config)) {
                log.info("uaa: Registering CORS filter with allowed origins: {}", config.getAllowedOrigins());
                corsConfigurationSource.registerCorsConfiguration(CORS_PATH_PATTERN, config);
            } else {
                log.warn("uaa: CORS configuration is empty or invalid, skipping registration.");
            }

            return new CorsFilter(corsConfigurationSource);
        }

        @Bean
        public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
            this.preBuild(http);
            SecurityFilterChain chain = http.build();
            this.postBuild(chain);

            return chain;
        }

        @Bean
        public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

        private void preBuild(HttpSecurity http) throws Exception {
            this.tryConfigure(http);
        }

        private void tryConfigure(HttpSecurity http) throws Exception {
            String[] pathArray = this.determineIgnorePaths();

            HttpSecurity proxy = http
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(this.userDetailsService);

            if (this.determineCorsIsEnabled()) {
                proxy.addFilterBefore(
                    this.corsFilter(),
                    UsernamePasswordAuthenticationFilter.class
                );
            }

            // @formatter:off
            proxy
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception ->
                    exception
                        .authenticationEntryPoint(this.authenticationEntryPoint)
                        .accessDeniedHandler(this.accessDeniedHandler)
                )
                .headers(headers ->
                    headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .sessionManagement(session ->
                    session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(requester ->
                    requester
                        .requestMatchers(HttpMethod.OPTIONS, AntPathConstants.ALL).permitAll()
                        .requestMatchers(pathArray).permitAll()
                        .anyRequest().authenticated()
                );

            proxy.with(this.securityConfigurer(), (x) -> { });
            // @formatter:on
        }

        private JwtSecurityConfigurer securityConfigurer() {
            return this.securityConfigurer;
        }

        private String[] determineIgnorePaths() {
            return this.ignorePathDeterminer.collectIgnorePathArray();
        }

        private void postBuild(SecurityFilterChain chain) {

        }

        private boolean determineCorsIsEnabled() {
            return
                this.environment.getProperty(
                    CORS_ENABLED_PROPERTY_KEY,
                    Boolean.class,
                    true
                );
        }

        private boolean isCorsConfigValid(CorsConfiguration config) {
            return Collections.isNotEmpty(config.getAllowedOrigins())
                || Collections.isNotEmpty(config.getAllowedOriginPatterns());
        }
    }
}
