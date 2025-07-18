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
package io.github.photowey.riff.infras.authentication.api.token;

import io.github.photowey.riff.infras.authentication.api.encryptor.SubjectEncryptor;
import io.github.photowey.riff.infras.authentication.property.getter.SecurityPropertiesGetter;

/**
 * {@code TokenParserService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public interface TokenParserService extends SecurityPropertiesGetter {

    String TOKEN_SEPARATOR = " ";

    /**
     * Acquire {@link SubjectEncryptor} instance.
     *
     * @return the {@link SubjectEncryptor} instance.
     */
    default SubjectEncryptor subjectEncryptor() {
        return this.beanFactory().getBean(SubjectEncryptor.class);
    }
}
