package com.example.customer.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID cif;
    private String name;
    private String surname;
    private String username;
    private LocalDate birthDate;
    private BigDecimal balance;
}
