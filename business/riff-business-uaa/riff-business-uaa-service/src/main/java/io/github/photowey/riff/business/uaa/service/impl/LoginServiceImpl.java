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
package io.github.photowey.riff.business.uaa.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import io.github.photowey.riff.business.uaa.core.domain.dto.RefreshTokenDTO;
import io.github.photowey.riff.business.uaa.core.domain.dto.TokenDTO;
import io.github.photowey.riff.business.uaa.core.domain.payload.LoginPayload;
import io.github.photowey.riff.business.uaa.service.LoginService;
import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.username.Username;
import io.github.photowey.riff.infras.authentication.jjwt.token.context.DefaultTokenContext;
import io.github.photowey.riff.infras.authentication.jjwt.token.context.TokenContext;
import io.github.photowey.riff.infras.authentication.property.SecurityProperties;
import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.util.Strings;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractApplicationContextHolder;
import io.jsonwebtoken.JwtBuilder;

/**
 * {@code LoginServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/23
 */
@Service
public class LoginServiceImpl extends AbstractApplicationContextHolder implements LoginService {

    @Override
    public TokenDTO login(LoginPayload payload) {
        payload.preAction();

        this.checkCaptcha(payload);

        // TODO Reuse rules?
        Optional<TokenDTO> reusedOpt = this.tryReused(payload);
        if (reusedOpt.isPresent()) {
            return reusedOpt.get();
        }

        TokenDTO response = this.integrated(payload, (authentication, scopes) -> {
            scopes.add(payload.determineAuthenticationMode());
        }, (authentication, roles) -> {
            roles.add(AuthorityConstants.AUTHORITY_ROLE_NORMAL);
        }, (token) -> {
            this.postAsyncAction(token, payload);
        });

        payload.postAction();

        return response;
    }

    private Optional<TokenDTO> tryReused(LoginPayload payload) {
        return Optional.empty();

    }

    private void postAsyncAction(TokenDTO token, LoginPayload payload) {
        // TODO
    }

    public TokenDTO integrated(
        LoginPayload payload,
        BiConsumer<Authentication, Set<String>> scopeFx,
        BiConsumer<Authentication, Set<String>> roleFx,
        Consumer<TokenDTO> fx) {
        this.enhanceAuthenticationHeader(payload);

        Authentication authentication =
            this.authenticate(payload, this::populateAccountUsernameProxy);

        Set<String> scopes = this.populateScopes(payload, authentication);
        Set<String> roles = this.populateRoles(payload, authentication);
        scopeFx.accept(authentication, scopes);
        roleFx.accept(authentication, roles);

        TokenContext ctx = DefaultTokenContext.builder()
            .authentication(authentication)
            .rememberMe(payload.determineIsRememberMe())
            .callback((builder) -> {
                this.ext(payload, scopes, roles, builder);
            })
            .now(System.currentTimeMillis())
            .build();

        String token = this.auth().jwt().createToken(ctx);
        String refreshToken = this.auth().jwt().createRefreshToken(ctx);

        TokenDTO dto = this.toToken(ctx, token, refreshToken, false);
        fx.accept(dto);

        return dto;
    }

    private Authentication authenticate(
        LoginPayload payload, Function<LoginPayload, String> fx) {
        return this.authenticate(payload, LoginPayload::platform, fx);
    }

    private Authentication authenticate(
        LoginPayload payload,
        Function<LoginPayload, String> platformFx,
        Function<LoginPayload, String> proxyFx) {
        String proxy = proxyFx.apply(payload);

        Username username = Username.builder()
            .app(payload.app())
            .username(proxy)
            .platform(platformFx.apply(payload))
            .client(payload.client())
            .rememberMe(payload.rememberMe())
            .build();

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username.compact(), payload.password());

        Authentication authentication =
            this.auth().manager().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private void enhanceAuthenticationHeader(LoginPayload payload) {
        this.enhancePlatform(payload);
        this.enhanceApp(payload);
        this.enhanceClient(payload);
    }

    private void enhancePlatform(LoginPayload payload) {
        if (Strings.isNotEmpty(payload.platform())) {
            return;
        }
        String platformHeader = this.auth().requestDeterminer().determinePlatformHeader();
        if (Strings.isNotEmpty(platformHeader)) {
            payload.setPlatform(platformHeader);

            return;
        }

        payload.setPlatform(AuthorityConstants.DEFAULT_TENANT);
    }

    private void enhanceApp(LoginPayload payload) {
        if (Strings.isNotEmpty(payload.app())) {
            return;
        }
        String appHeader = this.auth().requestDeterminer().determineAppHeader();
        if (Strings.isNotEmpty(appHeader)) {
            payload.setApp(appHeader);
            return;
        }

        payload.setApp(AuthorityConstants.DEFAULT_PLATFORM);
    }

    private void enhanceClient(LoginPayload payload) {
        if (Strings.isNotEmpty(payload.client())) {
            return;
        }

        String clientHeader = this.auth().requestDeterminer().determineClientHeader();
        if (Strings.isNotEmpty(clientHeader)) {
            payload.setClient(clientHeader);

            return;
        }

        payload.setClient(AuthorityConstants.DEFAULT_CLIENT);
    }

    // ----------------------------------------------------------------

    private Set<String> populateScopes(
        LoginPayload payload,
        Authentication authenticate) {
        return this.populateScopes(payload, authenticate, false);
    }

    private Set<String> populateScopes(
        LoginPayload payload, Authentication authenticate, boolean handle2fa) {
        LoginUser principal = (LoginUser) authenticate.getPrincipal();

        payload.setUserId(principal.userId());
        payload.setLoginUser(principal);

        Set<String> scopes = new HashSet<>(principal.scopes());
        this.enhanceScopes(payload, scopes);

        return scopes;
    }

    private Set<String> populateRoles(LoginPayload payload, Authentication authenticate) {
        return this.populateRoles(payload, authenticate, false);
    }

    private Set<String> populateRoles(
        LoginPayload x, Authentication authenticate, boolean handle2fa) {
        LoginUser principal = (LoginUser) authenticate.getPrincipal();

        Set<String> roles = new HashSet<>(principal.roles());
        this.enhanceRoles(roles);

        return roles;
    }

    private void enhanceScopes(LoginPayload payload, Set<String> scopes) {
        // TODO Optimize
        scopes.add(payload.tenant());
        scopes.add(payload.platform());
        scopes.add(payload.app());
        scopes.add(payload.client());
    }

    private void enhanceRoles(Set<String> roles) {

    }

    // ----------------------------------------------------------------

    private void ext(
        LoginPayload payload, Set<String> scopes, Set<String> roles, JwtBuilder builder) {
        builder.claim(AuthorityConstants.CLAIM_SCOPE_KEY, new ArrayList<>(scopes));
        if (CollectionUtils.isNotEmpty(roles)) {
            builder.claim(AuthorityConstants.CLAIM_ROLE_KEY, roles);
        }

        SecurityProperties.Auth auth = this.securityProperties().auth();
        builder.claim(AuthorityConstants.CLAIM_ISSUER_KEY, auth.issuer().uri());
        builder.claim(AuthorityConstants.CLAIM_ISSUE_AT_KEY, this.now());
        builder.claim(AuthorityConstants.CLAIM_JWT_ID_KEY, this.jwtId());
        builder.claim(AuthorityConstants.CLAIM_AUDIT_KEY, this.audit(payload.platform()));
        builder.claim(AuthorityConstants.CLAIM_CLIENT_KEY, payload.client());
    }

    // ----------------------------------------------------------------

    private TokenDTO toToken(
        TokenContext ctx,
        String token,
        String refreshToken,
        boolean twofa) {

        SecurityProperties.Jwt jwt = this.securityProperties().auth().jwt();
        long expiresIn = ctx.rememberMe()
            ? jwt.tokenValidityInSecondsForRememberMe()
            : jwt.tokenValidityInSeconds();

        RefreshTokenDTO refresh = RefreshTokenDTO.builder()
            .enabled(1)
            .token(refreshToken)
            .type(AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER)
            .issuedAt(ctx.now())
            .expiresIn(jwt.refreshTokenValidityInSeconds())
            .build();

        return TokenDTO.builder()
            .token(token)
            .twofa(twofa ? 1 : 0)
            .type(AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER)
            .issuedAt(ctx.now())
            .expiresIn(expiresIn)
            .refreshToken(refresh)
            .build();
    }

    // ----------------------------------------------------------------

    private String populateAccountUsernameProxy(LoginPayload payload) {
        return StringFormatter.format(
            AuthorityConstants.PROTOCOL_TEMPLATE,
            payload.protocol(),
            payload.username()
        );
    }

    // ----------------------------------------------------------------

    private void checkCaptcha(LoginPayload payload) {
        // TODO
    }
}
