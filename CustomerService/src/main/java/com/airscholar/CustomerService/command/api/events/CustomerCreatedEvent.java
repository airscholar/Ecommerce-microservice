package com.airscholar.CustomerService.command.api.events;

import com.airscholar.CommonService.data.CardDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreatedEvent {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private CardDetails cardDetails;
}
