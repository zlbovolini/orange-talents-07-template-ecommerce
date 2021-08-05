package com.github.zlbovolini.mercadolivre.security;

import com.github.zlbovolini.mercadolivre.authentication.TokenService;
import com.github.zlbovolini.mercadolivre.user.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);

        boolean validToken = tokenService.isValid(token);

        if (validToken) {
            authenticate(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticate(String token) {
        Long id = tokenService.getUserId(token);
        userRepository.findById(id)
                .ifPresent(user -> {
                    UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(credentials);
                });
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}
