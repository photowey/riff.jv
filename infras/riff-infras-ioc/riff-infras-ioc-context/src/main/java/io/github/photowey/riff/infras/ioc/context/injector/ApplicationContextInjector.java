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

import io.github.photowey.riff.infras.ioc.core.enums.holder.ApplicationContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

/**
 * {@code ApplicationContextInjector}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public class ApplicationContextInjector implements ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;

        this.inject();
    }

    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    public ConfigurableApplicationContext configurableApplicationContext() {
        return (ConfigurableApplicationContext) this.applicationContext();
    }

    @Override
    public void destroy() throws Exception {
        this.clean();
    }

    private void inject() {
        ApplicationContextHolder.INSTANCE.applicationContext(this.configurableApplicationContext());
    }

    private void clean() {
        ApplicationContextHolder.clean();
    }
}
