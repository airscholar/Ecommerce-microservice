package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CustomerService.command.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(String customerId);
}
