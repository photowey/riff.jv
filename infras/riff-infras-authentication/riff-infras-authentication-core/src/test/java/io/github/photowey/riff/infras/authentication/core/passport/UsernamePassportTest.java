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
package io.github.photowey.riff.infras.authentication.core.passport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code UsernamePassportTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
class UsernamePassportTest {

    @Test
    void testCompact() {
        UsernamePassport passport = UsernamePassport.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .client("web")
            .userId(10086L)
            .username("admin")
            .mobile("18888888888")
            .type(1)
            .build();

        String compacted = passport.compact();
        String expected = "saas:@:saas:@:boss:@:web:@:10086:@:admin:@:18888888888:@:1";

        Assertions.assertEquals(expected, compacted);
    }

    @Test
    void testCompact_mobile_null() {
        UsernamePassport passport = UsernamePassport.builder()
            .tenant("saas")
            .platform("saas")
            .app("boss")
            .client("web")
            .userId(10086L)
            .username("admin")
            //.mobile("18888888888")
            .type(1)
            .build();

        String compacted = passport.compact();
        String expected = "saas:@:saas:@:boss:@:web:@:10086:@:admin:@:-:@:1";

        Assertions.assertEquals(expected, compacted);
    }

    @Test
    void testParse() {
        String compacted = "saas:@:saas:@:boss:@:web:@:10086:@:admin:@:18888888888:@:1";
        UsernamePassport passport = UsernamePassport.parse(compacted);

        Assertions.assertEquals("saas", passport.tenant());
        Assertions.assertEquals("saas", passport.platform());
        Assertions.assertEquals("boss", passport.app());
        Assertions.assertEquals("web", passport.client());
        Assertions.assertEquals(10086L, passport.userId());
        Assertions.assertEquals("admin", passport.username());
        Assertions.assertEquals("18888888888", passport.mobile());
        Assertions.assertEquals(1, passport.type());
    }

    @Test
    void testParse_mobile_null() {
        String compacted = "saas:@:saas:@:boss:@:web:@:10086:@:admin:@:-:@:1";
        UsernamePassport passport = UsernamePassport.parse(compacted);

        Assertions.assertEquals("saas", passport.tenant());
        Assertions.assertEquals("saas", passport.platform());
        Assertions.assertEquals("boss", passport.app());
        Assertions.assertEquals("web", passport.client());
        Assertions.assertEquals(10086L, passport.userId());
        Assertions.assertEquals("admin", passport.username());
        Assertions.assertNull(passport.mobile());
        Assertions.assertEquals(1, passport.type());
    }

}
