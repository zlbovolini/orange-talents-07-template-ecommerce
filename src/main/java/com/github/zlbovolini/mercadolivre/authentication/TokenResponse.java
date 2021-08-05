package com.github.zlbovolini.mercadolivre.authentication;

import java.time.Instant;

class TokenResponse {

    private final String token;
    private final String type;
    private final Instant expiration;

    TokenResponse(Token token) {
        this.token = token.getToken();
        this.type = token.getType();
        this.expiration = token.getExpiration();
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Instant getExpiration() {
        return expiration;
    }
}
