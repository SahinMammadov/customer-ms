package com.example.customer.controller;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateBalanceRequest;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{cif}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("cif") UUID cif) {
        CustomerResponse customerResponse = customerService.getCustomerByCif(cif);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerResponses = customerService.getAllCustomers();
        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }

    @PutMapping("/{cif}/balance")
    public void updateBalance(@PathVariable("cif") UUID cif, @RequestBody UpdateBalanceRequest request) {
        customerService.updateBalance(cif, request.getAmount());
    }
}
