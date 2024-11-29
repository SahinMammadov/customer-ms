package com.example.customer.service.impl;

import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("50.00");

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        var customer = Customer.builder()
                .cif(UUID.randomUUID())
                .name(request.getName())
                .surname(request.getSurname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(request.getBirthDate())
                .balance(request.getBalance() != null ? request.getBalance() : DEFAULT_BALANCE)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        log.debug("Customer(cif={}) created", savedCustomer.getCif());
        return mapCustomerToCustomerResponse(savedCustomer);
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

    @Override
    @Transactional
    public void updateBalance(UUID cif, BigDecimal amount) {
        Customer customer = customerRepository.findByCif(cif)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setBalance(amount);
        log.debug("Customer (cif={}) balance updated to {}", cif, amount);
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    private CustomerResponse mapCustomerToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCif(),
                customer.getName(),
                customer.getSurname(),
                customer.getUsername(),
                customer.getBirthDate(),
                customer.getBalance()
        );
    }
}
