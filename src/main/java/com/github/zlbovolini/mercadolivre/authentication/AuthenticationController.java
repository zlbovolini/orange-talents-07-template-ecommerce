package com.github.zlbovolini.mercadolivre.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authenticationToken = loginRequest.toAuthentication();

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            Token token = tokenService.generate(authentication);

            return ResponseEntity.ok(new TokenResponse(token));
        } catch (AuthenticationException ae) {
            return ResponseEntity.badRequest().build();
        }


    }
}
