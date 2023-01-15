package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CommonService.data.CardDetails;
import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private CardDetails cardDetails;
}
