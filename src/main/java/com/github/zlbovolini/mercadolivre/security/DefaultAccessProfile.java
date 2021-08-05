package com.github.zlbovolini.mercadolivre.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class DefaultAccessProfile implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    private final List<Permission> permissions = new ArrayList<>();

    @Deprecated
    DefaultAccessProfile() {}

    DefaultAccessProfile(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    Collection<? extends GrantedAuthority> getDefaultAccessProfilePermissions() {
        return permissions;
    }
}
