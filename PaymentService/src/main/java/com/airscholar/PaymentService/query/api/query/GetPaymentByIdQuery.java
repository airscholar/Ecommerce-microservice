package com.airscholar.PaymentService.query.api.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetPaymentByIdQuery {
    private Long id;
}
