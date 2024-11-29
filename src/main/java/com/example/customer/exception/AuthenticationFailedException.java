package com.example.customer.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends RuntimeException {
    private HttpStatus httpStatus;

    public AuthenticationFailedException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }
}
