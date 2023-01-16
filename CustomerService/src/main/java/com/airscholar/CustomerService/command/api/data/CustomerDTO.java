package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CustomerService.command.api.entity.CardDetails;
import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private CardDetails cardDetails;
}
