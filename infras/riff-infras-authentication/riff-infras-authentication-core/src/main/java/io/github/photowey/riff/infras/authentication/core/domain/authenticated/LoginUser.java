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
package io.github.photowey.riff.infras.authentication.core.domain.authenticated;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.github.photowey.riff.infras.authentication.core.constant.AuthorityConstants;
import io.github.photowey.riff.infras.authentication.core.enums.AuthenticationDictionary;
import io.github.photowey.riff.infras.common.util.Arrays;
import io.github.photowey.riff.infras.common.util.Collections;
import io.github.photowey.riff.infras.common.util.Objects;
import io.github.photowey.riff.infras.common.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code LoginUser}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, IScope, IRole {

    @Serial
    private static final long serialVersionUID = -1908946047508237286L;

    private String tenant;
    private String platform;
    private String app;
    private String client;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;
    private String mobile;
    private String fullname;
    private String compacted;

    /**
     * 1: Boss(web|dashboard)
     * 2: OAuth Client
     *
     * @see AuthenticationDictionary.User.Type
     */
    private Integer type;
    /**
     * 1: unactivated
     * 2: activated
     * 4: frozen
     * 8: forbidden
     * 16: expired
     * 32: locked
     *
     * @see AuthenticationDictionary.User.Status
     */
    private Integer status;
    private Integer authenticationStatus;
    /**
     * 0: no two-factor authentication
     * 1: two-factor authentication
     */
    private Integer twofaEnabled;

    @JsonIgnore
    private String password = AuthorityConstants.DEFAULT_PASSWORD;

    @JsonIgnore
    private Set<String> authoritySet = new HashSet<>();
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    private Set<String> scopes = new HashSet<>();
    private Set<String> roles = new HashSet<>();

    @JsonIgnore
    private boolean dummy = false;
    private String token;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public LoginUser(Set<String> authoritySet) {
        this.authoritySet = authoritySet;
        this.dummy = true;
    }

    public LoginUser(Long userId, Set<String> authoritySet) {
        this.userId = userId;
        this.authoritySet = authoritySet;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return Strings.defaultIfEmpty(this.password, AuthorityConstants.DEFAULT_PASSWORD);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(this.authoritySet).orElse(new HashSet<>(0)).stream()
            .map(authority -> {
                String it =
                    authority.startsWith(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX)
                        ? authority
                        : AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX + authority;
                return new SimpleGrantedAuthority(it);
            }).collect(Collectors.toSet());
    }

    public void appendScopes(String... scopes) {
        if (Arrays.isNotEmpty(scopes)) {
            if (Objects.isNull(this.scopes)) {
                this.scopes = Arrays.asMutableSet(scopes);

                return;
            }

            this.scopes.addAll(Arrays.asImmutableSet(scopes));
        }
    }

    public void appendScopes(Set<String> scopeSet) {
        if (Collections.isNotEmpty(scopeSet)) {
            if (Objects.isNull(this.scopes)) {
                this.scopes = scopeSet;

                return;
            }

            this.scopes.addAll(scopeSet);
        }
    }

    public void appendAuthoritySets(String... authorities) {
        if (Arrays.isNotEmpty(authorities)) {
            if (Objects.isNull(this.authoritySet)) {
                this.authoritySet = Arrays.asMutableSet(authorities);

                return;
            }

            this.authoritySet.addAll(Arrays.asImmutableList(authorities));
        }
    }

    public void appendAuthoritySets(Set<String> authoritySet) {
        if (Collections.isNotEmpty(authoritySet)) {
            if (Objects.isNull(this.authoritySet)) {
                this.authoritySet = authoritySet;

                return;
            }

            this.authoritySet.addAll(authoritySet);
        }
    }

    public void appendRoles(String... roles) {
        if (Arrays.isNotEmpty(roles)) {
            if (Objects.isNull(this.roles)) {
                this.roles = Arrays.asMutableSet(roles);

                return;
            }

            this.roles.addAll(Arrays.asImmutableSet(roles));
        }
    }

    public void appendRoles(Set<String> roleSet) {
        if (Collections.isNotEmpty(roleSet)) {
            if (Objects.isNull(this.roles)) {
                this.roles = roleSet;

                return;
            }

            this.roles.addAll(roleSet);
        }
    }

    // ----------------------------------------------------------------

    public boolean determineScopeIsNotContains(String scope) {
        return !this.determineScopeIsContains(scope);
    }

    public boolean determineScopeIsContains(String scope) {
        return this.scopes().stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet())
            .contains(scope);
    }

    public boolean determineRoleIsNotContains(String role) {
        return !this.determineRoleIsContains(role);
    }

    public boolean determineRoleIsContains(String role) {
        return this.getRoles()
            .stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet())
            .contains(role);
    }

    public boolean determineAuthoritySetIsNotContains(String authority) {
        return !this.determineAuthoritySetIsContains(authority);
    }

    public boolean determineAuthoritySetIsContains(String authority) {
        return this.getAuthoritySet()
            .stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet()).contains(authority);
    }

    // ----------------------------------------------------------------

    public static boolean determineAuthenticated(LoginUser loginUser) {
        return !loginUser.isDummy();
    }

    // ----------------------------------------------------------------

    public boolean determineIsDummy() {
        return this.isDummy();
    }

    // ----------------------------------------------------------------

    public Set<String> getRoles() {
        if (null == this.roles) {
            this.roles = Collections.emptySet();
        }

        return this.roles;
    }

    public Set<String> roles() {
        return this.getRoles();
    }

    public Set<String> getScopes() {
        if (null == this.scopes) {
            this.scopes = Collections.emptySet();
        }

        return this.scopes;
    }

    public Set<String> scopes() {
        return this.getScopes();
    }

    public Set<String> getAuthoritySet() {
        if (null == this.authoritySet) {
            this.authoritySet = Collections.emptySet();
        }

        return this.authoritySet;
    }

    // ----------------------------------------------------------------


    public void injectPrincipalAuthorities(AuthenticatedPrincipal principal) {
        this.appendAuthoritySets(principal.authorities());
        this.appendRoles(principal.roles());
        this.appendScopes(principal.scopes());
        this.setFullname(principal.fullname());
    }

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static LoginUser newDummyLoginUser() {
        Set<String> emptySet = Collections.emptySet();

        return LoginUser.builder()
            .tenant(AuthorityConstants.DEFAULT_TENANT)
            .platform(AuthorityConstants.DEFAULT_PLATFORM)
            .app(AuthorityConstants.DEFAULT_APP)
            .client(AuthorityConstants.DEFAULT_CLIENT)
            // ----------------------------------------------------------------
            .userId(AuthorityConstants.DUMMY_LOGIN_USER_ID)
            .username(AuthorityConstants.DEFAULT_ANONYMOUS_USERNAME)
            // ----------------------------------------------------------------
            .dummy(true)
            // ----------------------------------------------------------------
            .authoritySet(emptySet)
            .roles(emptySet)
            .scopes(emptySet)
            // ----------------------------------------------------------------
            .type(AuthenticationDictionary.User.Type.BOSS.value())
            .status(AuthenticationDictionary.User.Status.UNACTIVATED.value())
            // ----------------------------------------------------------------
            .authenticationStatus(0)
            .build();
    }

    // ----------------------------------------------------------------

    public Long userId() {
        return userId;
    }

    public String stringUserId() {
        if (Objects.isNull(this.userId)) {
            return null;
        }

        return String.valueOf(userId);
    }

    public String username() {
        return username;
    }

    public String fullname() {
        return fullname;
    }

    public String compacted() {
        return compacted;
    }

    public String mobile() {
        return mobile;
    }

    public String client() {
        return client;
    }

    public Integer type() {
        return type;
    }

    public Integer status() {
        return status;
    }

    public String token() {
        return token;
    }
}

