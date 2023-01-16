package com.airscholar.CustomerService.command.api.service;

import com.airscholar.CustomerService.command.api.data.CustomerRepository;
import com.airscholar.CustomerService.command.api.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return this.customerRepository.findById(id).get();
    }

    public Customer getCustomerByCustomerId(String customerId) {
        return this.customerRepository.findByCustomerId(customerId);
    }
}
