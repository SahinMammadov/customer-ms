package com.example.customer.service;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerByCif(UUID cif);
    List<CustomerResponse> getAllCustomers();
    void updateBalance(UUID cif, BigDecimal amount);

    Optional<Customer> findCustomerByUsername(String username);
}
