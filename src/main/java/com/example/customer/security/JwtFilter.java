package com.example.customer.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final TokenProperties tokenProperties;

    public JwtFilter(
            JwtTokenProvider tokenProvider,
            TokenProperties tokenProperties
    ) {
        this.tokenProvider = tokenProvider;
        this.tokenProperties = tokenProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.resolveToken(request);

        if (StringUtils.hasText(token) && tokenProvider.isTokenValid(token)) {
            Claims claims = tokenProvider.getClaims(token);
            Authentication authentication = tokenProvider.getAuthentication(claims);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String tokenPrefix = tokenProperties.getTokenPrefix();

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(tokenPrefix.length());
        }

        return null;
    }
}
