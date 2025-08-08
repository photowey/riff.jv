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
package io.github.photowey.riff.business.job.core.secret;

import io.github.photowey.riff.infras.common.constant.CommonConstants;
import io.github.photowey.riff.infras.common.datetime.Datetimes;
import io.github.photowey.riff.infras.common.nanoid.NanoId;
import io.github.photowey.riff.infras.common.thrower.AssertionErrors;
import io.github.photowey.riff.infras.crypto.integrated.Cryptos;

/**
 * {@code OauthSecret}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/08
 */
public final class OauthSecret {

    private OauthSecret() {
        AssertionErrors.throwz(OauthSecret.class);
    }

    /**
     * yyyyMMdd + NanoId(24)
     *
     * @return the accessKey
     */
    public static String genAccessKey() {
        return Datetimes.today() + NanoId.randomNumberNanoId(CommonConstants.Access.ACCESS_KEY_RANDOM_LENGTH);
    }

    /**
     * NanoId(56) + tail(MD5(accessKey).substring(0, 8))
     *
     * @param accessKey the accessKey
     * @return the accessSecret
     */
    public static String genAccessSecret(String accessKey) {
        String tail = Cryptos.HASH.md5(accessKey).substring(0, CommonConstants.Access.ACCESS_SECRET_TAIL_LENGTH);
        return NanoId.randomNanoId(CommonConstants.Access.ACCESS_SECRET_RANDOM_LENGTH) + tail;
    }
}
