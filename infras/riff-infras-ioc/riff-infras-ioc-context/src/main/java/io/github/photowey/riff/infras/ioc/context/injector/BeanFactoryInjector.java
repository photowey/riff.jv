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
package io.github.photowey.riff.infras.ioc.context.injector;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import io.github.photowey.riff.infras.ioc.core.enums.holder.BeanFactoryHolder;

/**
 * {@code BeanFactoryInjector}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public class BeanFactoryInjector implements BeanFactoryPostProcessor, DisposableBean {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {
        this.beanFactory = beanFactory;

        this.inject();
    }

    @Override
    public void destroy() throws Exception {
        this.clean();
    }

    private void inject() {
        BeanFactoryHolder.INSTANCE.beanFactory(this.beanFactory);
    }

    private void clean() {
        BeanFactoryHolder.clean();
    }
}
