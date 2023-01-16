package com.airscholar.CustomerService.command.api.aggregate;

import com.airscholar.CustomerService.command.api.command.CreateCustomerCommand;
import com.airscholar.CustomerService.command.api.entity.CardDetails;
import com.airscholar.CustomerService.command.api.events.CustomerCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Aggregate
public class CustomerAggregate {
    @AggregateIdentifier
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private CardDetails cardDetails;

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand){
        log.info("Handling CreateCustomerCommand {}", createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();

        BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);

        apply(customerCreatedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event){
        this.customerId = event.getCustomerId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
        this.address = event.getAddress();
        this.cardDetails = event.getCardDetails();
    }
}
