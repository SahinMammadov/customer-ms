package com.example.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBalanceRequest {
    BigDecimal amount;
}
