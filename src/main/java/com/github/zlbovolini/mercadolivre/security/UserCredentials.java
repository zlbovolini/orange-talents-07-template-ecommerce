package com.github.zlbovolini.mercadolivre.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserCredentials {

    String getUsername();

    String getHashedPassword();

    Collection<? extends GrantedAuthority> getAuthorities();
}
