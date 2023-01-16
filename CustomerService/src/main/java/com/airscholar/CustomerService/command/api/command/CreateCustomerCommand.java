package com.airscholar.CustomerService.command.api.command;

import com.airscholar.CustomerService.command.api.entity.CardDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCommand {
    @TargetAggregateIdentifier
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private CardDetails cardDetails;

}
