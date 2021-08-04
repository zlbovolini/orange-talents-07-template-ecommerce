package com.github.zlbovolini.mercadolivre.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
class User {

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

    User() {}

    User(String email, Password password) {
        this.email = email;
        this.hashedPassword = password.getHashedPassword();
    }
}
