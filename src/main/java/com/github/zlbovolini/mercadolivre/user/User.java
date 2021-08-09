package com.github.zlbovolini.mercadolivre.user;

import com.github.zlbovolini.mercadolivre.security.DefaultAccessProfile;
import com.github.zlbovolini.mercadolivre.security.UserCredentials;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Representa o atributo utilizado para o <i>login</i> do usuário.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * Representa a senha criptografada do usuário.
     */
    @NotBlank
    private String hashedPassword;

    @NotNull
    private Instant createdAt = Instant.now();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<DefaultAccessProfile> accessProfiles = new ArrayList<>();

    @Deprecated
    User() {}

    User(String email, Password password) {
        this.email = email;
        this.hashedPassword = password.getHashedPassword();
    }

    Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accessProfiles;
    }
}
