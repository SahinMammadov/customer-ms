package com.example.customer.service;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerByCif(UUID cif);
    List<CustomerResponse> getAllCustomers();

}
