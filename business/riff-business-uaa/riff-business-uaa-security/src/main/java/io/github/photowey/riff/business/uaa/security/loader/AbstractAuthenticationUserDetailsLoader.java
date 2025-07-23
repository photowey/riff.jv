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
package io.github.photowey.riff.business.uaa.security.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.photowey.riff.business.uaa.security.checker.AuthenticationAccountChecker;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticationPrincipal;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.passport.UsernamePassport;
import io.github.photowey.riff.infras.authentication.core.username.Username;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractApplicationContextHolder;

/**
 * {@code AbstractAuthenticationUserDetailsLoader}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/22
 */
public abstract class AbstractAuthenticationUserDetailsLoader
    extends AbstractApplicationContextHolder implements AuthenticationUserDetailsLoader {

    protected ApplicationContext publisher() {
        return this.applicationContext();
    }

    // ----------------------------------------------------------------

    @Override
    public boolean supports(String proxy) {
        return proxy.startsWith(this.protocol());
    }

    @Override
    public UserDetails load(String proxy) {
        Username username = this.parseUsername(proxy);
        AuthenticationPrincipal principal = this.toAuthenticatedPrincipal(username);
        this.checkAccount(principal);

        return this.toLoginUser(principal, username);
    }

    // ----------------------------------------------------------------

    /**
     * The authentication protocol(type).
     * |- account://xxx
     * |- oauthclient://xxx
     * |- cmder://xxx
     * |- ...
     *
     * @return the authentication protocol(type).
     */
    public abstract String protocol();

    /**
     * Load the {@link AuthenticationPrincipal} by {@link Username}.
     *
     * @param proxy the username proxy {@link Username}.
     * @return the {@link AuthenticationPrincipal}.
     */
    public abstract AuthenticationPrincipal toAuthenticatedPrincipal(Username proxy);

    /**
     * Convert the {@link AuthenticationPrincipal} to {@link UsernamePassport}.
     *
     * @param principal the {@link AuthenticationPrincipal}.
     * @return the {@link UsernamePassport}.
     */
    public abstract UsernamePassport toUsernamePassport(AuthenticationPrincipal principal);

    // ----------------------------------------------------------------

    private void checkAccount(AuthenticationPrincipal principal) {
        Map<String, AuthenticationAccountChecker> beans =
            this.listableBeanFactory().getBeansOfType(AuthenticationAccountChecker.class);
        List<AuthenticationAccountChecker> accountCheckers = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(accountCheckers);

        String checker = this.checker();

        for (AuthenticationAccountChecker accountChecker : accountCheckers) {
            if (accountChecker.supports(checker)) {
                accountChecker.check(principal);
                break;
            }
        }
    }

    protected void checkAccountInvalid(AuthenticationPrincipal principal) {

    }

    protected Username parseUsername(String proxy) {
        String username = this.parseUsername(proxy, this.protocol());

        return Username.parse(username);
    }

    protected LoginUser toLoginUser(AuthenticationPrincipal principal, Username x) {
        UsernamePassport passport = this.toUsernamePassport(principal);
        LoginUser loginUser = this.populateLoginUser(principal, passport);

        this.enhanceLoginUser(loginUser);

        return loginUser;
    }

    @SuppressWarnings("all")
    private LoginUser populateLoginUser(
        AuthenticationPrincipal principal,
        UsernamePassport passport) {
        String compacted = passport.compact();

        return LoginUser.builder()
            .tenant(principal.tenant())
            .platform(principal.platform())
            .app(principal.app())
            .client(principal.client())
            // ----------------------------------------------------------------
            .userId(principal.userId())
            // ----------------------------------------------------------------
            .username(principal.username())
            .mobile(principal.mobile())
            .fullname(principal.fullname())
            .compacted(compacted)
            // ----------------------------------------------------------------
            .type(principal.type())
            .status(principal.status())
            .authenticationStatus(principal.authenticationStatus())
            .twofaEnabled(principal.twofaEnabled())
            // ----------------------------------------------------------------
            .password(principal.password())
            .build();
    }

    protected void enhanceLoginUser(LoginUser x) {

    }
}
