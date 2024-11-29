package com.example.customer.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.token")
public class TokenProperties {
    private String secret;
    private int accessTokenValidity;
    private int refreshTokenValidity;
    private String tokenPrefix;
}
