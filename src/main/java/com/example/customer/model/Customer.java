package com.example.customer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID cif;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = new BigDecimal("50.00");

}
