package com.github.zlbovolini.mercadolivre.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Deprecated
    Permission() {}

    Permission(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
