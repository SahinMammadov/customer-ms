package com.example.customer.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String ISSUER = "ABB";
    private final JwtParser jwtParser;
    private final Key key;

    private final TokenProperties tokenProperties;

    public JwtTokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        this.key = Keys.hmacShaKeyFor(tokenProperties.getSecret().getBytes());
        this.jwtParser = Jwts.parserBuilder()
                .requireIssuer(ISSUER)
                .setSigningKey(this.key)
                .build();
    }

    public String generateAccessToken(Authentication authentication) {
        long now = (new Date()).getTime();
        var validity = new Date(now + Duration.ofSeconds(tokenProperties.getAccessTokenValidity()).toMillis());
        return generateToken(authentication, validity, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(Authentication authentication) {
        long now = (new Date()).getTime();
        long ms = now + Duration.ofSeconds(tokenProperties.getRefreshTokenValidity()).toMillis();
        return generateToken(authentication, new Date(ms), TokenType.REFRESH_TOKEN);
    }

    private String generateToken(Authentication authentication, Date validity, TokenType tokenType) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(authentication.getName())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .setIssuedAt(new Date())
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT Token expired: {}, {}", e, e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException e) {
            log.error("Invalid JWT Token: {}, {}", e, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            jwtParser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.error("JWT Token expired: {}, {}", e, e.getMessage());
            return false;
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | MissingClaimException |
                 IllegalArgumentException e) {
            log.error("Invalid JWT Token: {}, {}", e, e.getMessage());
            return false;
        }

        return true;
    }

    public Authentication getAuthentication(Claims claims) {
        User user = new User(claims.getSubject(), "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}
