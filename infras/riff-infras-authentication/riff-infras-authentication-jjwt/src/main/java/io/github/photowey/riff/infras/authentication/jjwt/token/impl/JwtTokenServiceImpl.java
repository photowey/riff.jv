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
package io.github.photowey.riff.infras.authentication.jjwt.token.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import jakarta.annotation.Nonnull;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.photowey.riff.infras.authentication.api.encryptor.Encryptor;
import io.github.photowey.riff.infras.authentication.api.encryptor.SubjectEncryptor;
import io.github.photowey.riff.infras.authentication.api.loader.AuthenticatedPrincipalLoader;
import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.AuthenticatedPrincipal;
import io.github.photowey.riff.infras.authentication.core.domain.authenticated.LoginUser;
import io.github.photowey.riff.infras.authentication.core.enums.AuthenticationDictionary;
import io.github.photowey.riff.infras.authentication.core.passport.UsernamePassport;
import io.github.photowey.riff.infras.authentication.core.threadlocal.LoginUserHolder;
import io.github.photowey.riff.infras.authentication.core.util.Securitys;
import io.github.photowey.riff.infras.authentication.jjwt.token.JwtTokenService;
import io.github.photowey.riff.infras.authentication.jjwt.token.context.TokenContext;
import io.github.photowey.riff.infras.authentication.property.SecurityProperties;
import io.github.photowey.riff.infras.common.constant.CommonConstants;
import io.github.photowey.riff.infras.common.constant.datetime.DatetimeConstants;
import io.github.photowey.riff.infras.common.datetime.Datetimes;
import io.github.photowey.riff.infras.common.formatter.StringFormatter;
import io.github.photowey.riff.infras.common.nanoid.NanoId;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Lambdas;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.ioc.context.holder.AbstractBeanFactoryHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code JwtTokenServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@Slf4j
public class JwtTokenServiceImpl extends AbstractBeanFactoryHolder implements JwtTokenService {

    // @formatter:off

    @Override
    public String createToken(Authentication authentication, boolean rememberMe) {
        return this.createToken(authentication, rememberMe, (builder) -> { });
    }

    // @formatter:on

    @Override
    public String createToken(@Nonnull TokenContext ctx) {
        return this.jwt(ctx, (builder) -> {
            SecurityProperties.Jwt jwt = this.securityProperties().auth().jwt();
            long diff = ctx.rememberMe()
                ? jwt.tokenValidityInSecondsForRememberMe()
                : jwt.tokenValidityInSeconds();

            long validity = ctx.now() + diff * DatetimeConstants.MILLIS_OF_SECONDS;
            builder.expiration(new Date(validity));

            builder.claim(AuthorityConstants.CLAIM_TOKEN_TYPE_KEY, AuthorityConstants.CLAIM_TOKEN_TYPE_NORMAL);

            ctx.callback().accept(builder);
        });
    }

    @Override
    public String createRefreshToken(@Nonnull TokenContext ctx) {
        return this.jwt(ctx, (builder) -> {
            SecurityProperties.Jwt jwt = this.securityProperties().auth().jwt();
            long diff = jwt.refreshTokenValidityInSeconds();
            long validity = ctx.now() + diff * DatetimeConstants.MILLIS_OF_SECONDS;
            builder.expiration(new Date(validity));

            builder.claim(AuthorityConstants.CLAIM_TOKEN_TYPE_KEY, AuthorityConstants.CLAIM_TOKEN_TYPE_REFRESH);

            ctx.callback().accept(builder);
        });
    }

    @Override
    public Authentication tryAuthentication(String token) {
        return this.tryAuthentication(token,
            this.securityProperties().auth().jwt().secret()
        );
    }

    @Override
    public Authentication tryAuthentication(String token, String secretKey) {
        Claims claims = this.parseClaims(token, secretKey).getPayload();
        String authoritiesKey = this.securityProperties().auth().jwt().authorities();

        String granted = claims.get(authoritiesKey).toString().replaceAll("\\*\\.\\*", "");

        Set<String> authoritySet = this.populateAuthoritySet(granted);
        UsernamePassport passport = this.decryptSubject(claims.getSubject());

        this.determineUpgraded(passport);

        List<String> scopes = this.determineSets(claims, AuthorityConstants.CLAIM_SCOPE_KEY);
        List<String> roles = this.determineSets(claims, AuthorityConstants.CLAIM_ROLE_KEY);
        authoritySet.addAll(scopes);
        authoritySet.addAll(roles);

        LoginUser principal = this.toLoginUser(token, passport, authoritySet);

        scopes.forEach(principal::appendScopes);
        roles.forEach(principal::appendRoles);

        Set<? extends GrantedAuthority> authorities = Lambdas.toSet(authoritySet, SimpleGrantedAuthority::new);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, token, authorities);

        Securitys.authenticated(authentication);
        LoginUserHolder.set(principal);

        return authentication;
    }

    @Override
    public boolean validateToken(String token) {
        return this.validateToken(
            token,
            this.securityProperties().getAuth().getJwt().getSecret(),
            true
        );
    }

    @Override
    public boolean validateToken(String token, boolean quiet) {
        return this.validateToken(
            token,
            this.securityProperties().getAuth().getJwt().getSecret(),
            quiet
        );
    }

    @Override
    public boolean validateToken(String token, String secretKey, boolean quiet) {
        try {
            this.parseClaims(token, secretKey);
            return true;
        } catch (Throwable e) {
            if (!quiet) {
                log.error("jjwt: JWT token validation failed", e);
            }
        }

        return false;
    }

    @Override
    public Jws<Claims> parseClaims(@Nonnull String token, @Nonnull String secretKey) {
        String innerToken = this.cleanToken(token);

        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseSignedClaims(innerToken);
    }

    private String jwt(TokenContext ctx, Consumer<JwtBuilder> fx) {
        Authentication authentication = ctx.authentication();

        LoginUser principal = (LoginUser) authentication.getPrincipal();
        String name = principal.compacted();
        String subject = this.encryptSubject(name);

        SecurityProperties.Jwt jwt = this.securityProperties().auth().jwt();
        byte[] keyBytes = jwt.secret().getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, AuthorityConstants.TOKEN_ALGO_HS512);

        Map<String, Object> headers = this.populateHeaders();

        JwtBuilder builder = Jwts.builder()
            .subject(subject)
            .signWith(key, Jwts.SIG.HS512)
            .header().add(headers)
            .and()
            .claim(jwt.authorities(), AuthorityConstants.CLAIM_AUTHORITY_ALL);

        this.populateRoleClaim(authentication, builder);

        fx.accept(builder);

        return builder.compact();
    }

    private void populateRoleClaim(Authentication authentication, JwtBuilder builder) {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Set<String> roles = principal.determineRoles();
        builder.claim(AuthorityConstants.CLAIM_ROLE_KEY, roles);
    }

    private Map<String, Object> populateHeaders() {
        Map<String, Object> headers = new HashMap<>(4);
        headers.put("typ", "JWT");
        headers.put("kid", Datetimes.today() + NanoId.randomNumberNanoId(26));

        return headers;
    }

    private String encryptSubject(String subject) {
        String secret = this.securityProperties().auth().issuer().secret();
        String proxy = this.subjectEncryptor().encrypt(secret, subject);

        return this.compressSubject(proxy);
    }

    private UsernamePassport decryptSubject(String subject) {
        String secret = this.securityProperties().auth().issuer().secret();
        String mix = this.decompressSubject(subject);
        String proxy = this.subjectEncryptor().decrypt(secret, mix);

        UsernamePassport passport = UsernamePassport.parse(proxy);
        passport.setSubject(subject);

        return passport;
    }

    private String compressSubject(String proxy) {
        return StringFormatter.format(
            Encryptor.ENCRYPT_SUBJECT_TEMPLATE,
            Encryptor.SUBJECT_ENCRYPT_PREFIX,
            Encryptor.SUBJECT_ENCRYPT_SEPARATOR,
            proxy
        );
    }

    private String decompressSubject(String proxy) {
        String prefix = StringFormatter.format(
            SubjectEncryptor.DECRYPT_SUBJECT_TEMPLATE,
            SubjectEncryptor.SUBJECT_ENCRYPT_PREFIX,
            SubjectEncryptor.SUBJECT_ENCRYPT_SEPARATOR);
        return proxy.replaceAll(prefix, CommonConstants.Symbol.EMPTY);
    }

    private Set<String> populateAuthoritySet(String granted) {
        Set<String> authoritySet = new HashSet<>();
        if (StringUtils.isNotBlank(granted)) {
            authoritySet = Arrays
                .stream(granted.split(CommonConstants.Symbol.COMMA))
                .collect(Collectors.toSet());
        }

        return authoritySet;
    }

    @SuppressWarnings("unchecked")
    private List<String> determineSets(Claims claims, String claimKey) {
        List<String> items = (List<String>) claims.get(claimKey);
        if (Objects.nonNull(items)) {
            items = Collections.emptyList();
        }

        return items;
    }

    private void determineUpgraded(UsernamePassport passport) {
        // do nothing now.
    }

    @SuppressWarnings("all")
    private LoginUser toLoginUser(
        String token, UsernamePassport passport, Set<String> authoritySet) {
        LoginUser loginUser = LoginUser.builder()
            .tenant(passport.tenant())
            .platform(passport.platform())
            .app(passport.app())
            .client(passport.client())
            // ----------------------------------------------------------------
            .userId(passport.userId())
            // ----------------------------------------------------------------
            .username(passport.username())
            .compacted(passport.subject())
            .mobile(passport.mobile())
            // ----------------------------------------------------------------
            .authoritySet(authoritySet)
            // ----------------------------------------------------------------
            .token(token)
            .status(AuthenticationDictionary.User.Status.ACTIVATED.value())
            .authenticationStatus(AuthenticationDictionary.Authentication.Status.AUTHENTICATED.value())
            .dummy(false)
            .build();

        this.enhanceAuthoritySet(loginUser);

        return loginUser;
    }

    private void enhanceAuthoritySet(LoginUser loginUser) {
        Map<String, AuthenticatedPrincipalLoader> beans =
            this.listableBeanFactory().getBeansOfType(AuthenticatedPrincipalLoader.class);

        List<AuthenticatedPrincipalLoader> loaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(loaders);

        String strategy = this.securityProperties().auth().loader().name();

        for (AuthenticatedPrincipalLoader loader : loaders) {
            if (loader.supports(strategy)) {
                AuthenticatedPrincipal principal = loader.load(loginUser.getUserId());
                if (Objects.isNotNull(principal)) {
                    loginUser.injectPrincipalAuthorities(principal);
                }

                return;
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }
}

