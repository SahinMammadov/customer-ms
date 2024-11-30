package com.example.customer.service.impl;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = mapCustomerRequestToCustomer(customerRequest);
        customer = customerRepository.save(customer);
        return mapCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByCif(UUID cif) {
        Customer customer = customerRepository.findByCif(cif)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapCustomerToCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::mapCustomerToCustomerResponse)
                .collect(Collectors.toList());
    }

    private Customer mapCustomerRequestToCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setBirthDate(customerRequest.getBirthDate());
        customer.setBalance(customerRequest.getBalance() != null ? customerRequest.getBalance() : new BigDecimal("50.00"));
        return customer;
    }

    private CustomerResponse mapCustomerToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCif(),
                customer.getName(),
                customer.getSurname(),
                customer.getBirthDate(),
                customer.getBalance()
        );
    }
}
