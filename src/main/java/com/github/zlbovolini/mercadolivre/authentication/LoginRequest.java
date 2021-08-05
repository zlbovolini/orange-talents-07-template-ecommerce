package com.github.zlbovolini.mercadolivre.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class LoginRequest {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Size(min = 6)
    private final String password;

    @JsonCreator(mode = PROPERTIES)
    LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
