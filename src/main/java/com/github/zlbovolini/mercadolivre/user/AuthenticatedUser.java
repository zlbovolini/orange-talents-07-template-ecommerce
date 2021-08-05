package com.github.zlbovolini.mercadolivre.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class AuthenticatedUser implements UserDetails {

    @NotNull
    private Long id;

    @NotBlank
    private final String username;

    @NotBlank
    private final String hashedPassword;

    private final Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUser(@Valid @NotNull User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hashedPassword = user.getHashedPassword();
        this.authorities = user.getAuthorities();
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
