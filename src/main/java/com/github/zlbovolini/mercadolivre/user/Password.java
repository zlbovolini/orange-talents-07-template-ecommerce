package com.github.zlbovolini.mercadolivre.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Password {

    private final String hashedPassword;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Password(String plainPassword) {
        this.hashedPassword = passwordEncoder.encode(plainPassword);
    }

    /**
     * Criptografa a senha utilizando o algoritmo de criptografia bcrypt.
     * @param plainPassword senha em texto puro.
     * @return Objeto que representa uma senha criptografada.
     */
    static Password encode(@NotBlank @Size(min = 6) String plainPassword) {
        return new Password(plainPassword);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     *
     * @return senha criptografada.
     */
    String getHashedPassword() {
        return hashedPassword;
    }
}
