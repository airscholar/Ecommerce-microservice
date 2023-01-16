package com.airscholar.CustomerService.query.api.controller;

import com.airscholar.CustomerService.command.api.entity.Customer;
import com.airscholar.CustomerService.query.api.queries.GetAllCustomersQuery;
import com.airscholar.CustomerService.query.api.queries.GetCustomerByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerQueryController {
    private QueryGateway queryGateway;

    public CustomerQueryController( QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        GetAllCustomersQuery getAllCustomersQuery = new GetAllCustomersQuery();

        return queryGateway.query(getAllCustomersQuery, ResponseTypes.multipleInstancesOf(Customer.class)).join();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId){
        GetCustomerByIdQuery getCustomerByIdQuery = new GetCustomerByIdQuery(customerId);

        return queryGateway.query(getCustomerByIdQuery, ResponseTypes.instanceOf(Customer.class)).join();
    }
}
