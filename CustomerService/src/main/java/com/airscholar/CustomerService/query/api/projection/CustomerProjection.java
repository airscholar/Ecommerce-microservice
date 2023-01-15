package com.airscholar.CustomerService.query.api.projection;

import com.airscholar.CommonService.data.Customer;
import com.airscholar.CustomerService.command.api.service.CustomerService;
import com.airscholar.CustomerService.query.api.queries.GetAllCustomersQuery;
import com.airscholar.CustomerService.query.api.queries.GetCustomerByIdQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomerProjection {

    private CustomerService customerService;
    public CustomerProjection(CustomerService customerService) {
        this.customerService = customerService;
    }

    @QueryHandler
    public List<Customer> handle(GetAllCustomersQuery getAllCustomersQuery){
        log.info("handling GetAllCustomersQuery {}", getAllCustomersQuery);
        return this.customerService.getAllCustomers();
    }

    @QueryHandler
    public Customer handle(GetCustomerByIdQuery getCustomerByIdQuery){
        log.info("Handling GetCustomerByIdQuery {}", getCustomerByIdQuery);

        return customerService.getCustomerById(getCustomerByIdQuery.getId());
    }
}
