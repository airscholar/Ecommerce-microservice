package com.airscholar.CustomerService.command.api.events.handler;

import com.airscholar.CustomerService.command.api.data.CustomerRepository;
import com.airscholar.CustomerService.command.api.entity.Customer;
import com.airscholar.CustomerService.command.api.events.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventsHandler {

    private CustomerRepository customerRepository;

    public CustomerEventsHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @EventHandler
    public void on(CustomerCreatedEvent event){
        log.info("Handling customer created event => {}", event);
        Customer customer = new Customer();

        BeanUtils.copyProperties(event, customer);

        customerRepository.save(customer);
    }
}
