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
package io.github.photowey.riff.business.uaa.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.photowey.riff.business.uaa.security.loader.AuthenticationUserDetailsLoader;
import io.github.photowey.riff.business.uaa.security.service.DomainUserDetailsService;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

/**
 * {@code DomainUserDetailsServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public class DomainUserDetailsServiceImpl extends AbstractBeanFactoryHolder implements DomainUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String proxy) throws UsernameNotFoundException {
        return this.tryLoad(proxy);
    }

    public UserDetails tryLoad(String proxy) {
        Map<String, AuthenticationUserDetailsLoader> beans =
            this.listableBeanFactory().getBeansOfType(AuthenticationUserDetailsLoader.class);
        List<AuthenticationUserDetailsLoader> loaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(loaders);

        for (AuthenticationUserDetailsLoader loader : loaders) {
            if (loader.supports(proxy)) {
                return loader.load(proxy);
            }
        }

        throw new UnsupportedOperationException("Unreached here");
    }
}
