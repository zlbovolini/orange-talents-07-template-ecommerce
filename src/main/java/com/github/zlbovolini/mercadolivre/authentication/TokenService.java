package com.github.zlbovolini.mercadolivre.authentication;

import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Service
public class TokenService {

    private final String type = "Bearer";

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public Token generate(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + Long.parseLong(expiration));
        SecretKeySpec key = getSecretKeySpec();

        String token = Jwts.builder()
                .setIssuer("API Mercado Livre")
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new Token(token, type, expirationDate.toInstant());
    }

    public boolean isValid(String token) {
        SecretKeySpec key = getSecretKeySpec();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        SecretKeySpec key = getSecretKeySpec();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    private SecretKeySpec getSecretKeySpec() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);

        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
