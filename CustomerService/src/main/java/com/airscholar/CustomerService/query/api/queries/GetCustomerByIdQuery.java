package com.airscholar.CustomerService.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetCustomerByIdQuery {
    private Long Id;
}
