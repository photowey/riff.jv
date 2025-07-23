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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.photowey.riff.business.uaa.security.filter.JwtAuthenticationFilter;

/**
 * {@code JwtSecurityConfiguration}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public class JwtSecurityConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
    implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter authenticationFilter =
            this.beanFactory.getBean(JwtAuthenticationFilter.class);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
        this.inject();
    }

    private void inject() {

    }
}
