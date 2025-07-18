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
package io.github.photowey.riff.infras.crypto.algo.hash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.photowey.riff.infras.crypto.AbstractCryptoTest;

/**
 * {@code HashesTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/17
 */
class HashesTest extends AbstractCryptoTest {

    @Test
    void testMD5() {
        String data = MESSAGE;
        String md5 = Hashes.MD5.md5(data);

        Assertions.assertEquals("9c01c40a7689a8ab74949a4ec685411e", md5);
    }

    @Test
    void testSHA1() {
        String data = MESSAGE;
        String sha1 = Hashes.SHA1.sha1(data);

        Assertions.assertEquals("fe8d433758f97ab97f9ef56f73237ecb0de37a95", sha1);
    }

    @Test
    void testSHA256() {
        String data = MESSAGE;
        String sha256 = Hashes.SHA256.sha256(data);

        Assertions.assertEquals(
            "3478592bb7dae4d74076a4e9b2ec5a9e42630ff5c9fe8f3ef85a46e0738a89db",
            sha256
        );
    }

    @Test
    void testSHA384() {
        String data = MESSAGE;
        String sha384 = Hashes.SHA384.sha384(data);

        Assertions.assertEquals(
            "dd820faf0d846f42a6c2e319c2af30d5b7925b1b59fc83ce95530b15"
                + "1037832c7f37d0439cd00057234193faaf974ab5",
            sha384
        );
    }

    @Test
    void testSHA512() {
        String data = MESSAGE;
        String sha512 = Hashes.SHA512.sha512(data);

        Assertions.assertEquals(
            "8b9cb6e0236da0c9345aa081083f61a60a34917b5492f9146322167"
                + "a0ae2f634e48869a9d8ea9f93f08507050e4c1d8bef1fbf97edf36a321a221b7539b9487c",
            sha512
        );
    }

}
