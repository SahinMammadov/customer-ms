package com.example.customer.service.impl;

import com.example.customer.exception.AuthenticationFailedException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.dto.LoginRequest;
import com.example.customer.dto.TokenResponse;
import com.example.customer.model.Customer;
import com.example.customer.service.AuthenticationService;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        Customer customer = customerService.findCustomerByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        boolean passwordsMatched = passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword());
        if (!passwordsMatched) {
            throw new AuthenticationFailedException(HttpStatus.FORBIDDEN, "Authentication failed");
        }

        return new TokenResponse()
                .setAccessToken("")
                .setRefreshToken("");
    }
}
