package com.github.zlbovolini.mercadolivre.authentication;

import java.time.Instant;

public class Token {

    private final String token;
    private final String type;
    private final Instant expiration;

    public Token(String token, String type, Instant expiration) {
        this.token = token;
        this.type = type;
        this.expiration = expiration;
    }

    String getToken() {
        return token;
    }

    String getType() {
        return type;
    }

    Instant getExpiration() {
        return expiration;
    }
}
