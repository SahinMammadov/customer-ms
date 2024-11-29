package com.example.customer.dto;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
