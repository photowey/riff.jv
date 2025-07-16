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
package io.github.photowey.riff.infras.ioc.context.getter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code BeanFactoryGetter}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
public interface BeanFactoryGetter {

    /**
     * Acquire {@link BeanFactory}
     *
     * @return {@link BeanFactory}
     */
    BeanFactory beanFactory();

    /**
     * Acquire {@link ListableBeanFactory}
     *
     * @return {@link ListableBeanFactory}
     */
    default ListableBeanFactory listableBeanFactory() {
        return (ListableBeanFactory) this.beanFactory();
    }

    /**
     * Acquire {@link ConfigurableBeanFactory}
     *
     * @return {@link ConfigurableBeanFactory}
     */
    default ConfigurableBeanFactory configurableBeanFactory() {
        return (ConfigurableBeanFactory) this.listableBeanFactory();
    }

    /**
     * Acquire {@link ConfigurableListableBeanFactory}
     *
     * @return {@link ConfigurableListableBeanFactory}
     */
    default ConfigurableListableBeanFactory configurableListableBeanFactory() {
        return (ConfigurableListableBeanFactory) this.configurableBeanFactory();
    }

}
