package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CommonService.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
