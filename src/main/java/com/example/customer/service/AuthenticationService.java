package com.example.customer.service;

import com.example.customer.dto.LoginRequest;
import com.example.customer.dto.TokenResponse;

public interface AuthenticationService {
    TokenResponse login(LoginRequest loginRequest);

}
