package com.github.zlbovolini.mercadolivre.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.zlbovolini.mercadolivre.validation.constraint.Unique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class CreateUserRequest {

    @NotBlank
    @Email
    @Unique(entity = User.class, field = "email")
    private final String email;

    /**
     * Senha em formato de texto puro, sem criptografia.
     */
    @NotBlank
    @Size(min = 6)
    private final String password;

    @JsonCreator(mode = PROPERTIES)
    CreateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User toModel() {
        return new User(email, Password.encode(password));
    }
}
