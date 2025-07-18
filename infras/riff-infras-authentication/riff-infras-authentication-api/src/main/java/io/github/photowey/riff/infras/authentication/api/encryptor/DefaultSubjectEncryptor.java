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
package io.github.photowey.riff.infras.authentication.api.encryptor;

import jakarta.annotation.Nonnull;

import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.crypto.integrated.Cryptos;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;

/**
 * {@code DefaultSubjectEncryptor}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
public class DefaultSubjectEncryptor extends AbstractBeanFactoryHolder implements SubjectEncryptor {

    @Override
    public String encrypt(@Nonnull String key, String data) {
        if (Strings.isEmpty(data)) {
            return null;
        }

        return Cryptos.AES.CBC.PKCS5Padding.encrypt(key, data);
    }

    @Override
    public String decrypt(@Nonnull String key, String encrypted) {
        if (Strings.isEmpty(encrypted)) {
            return null;
        }

        return Cryptos.AES.CBC.PKCS5Padding.decrypt(key, encrypted);
    }
}
