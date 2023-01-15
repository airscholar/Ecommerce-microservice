package com.airscholar.CommonService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {
    private String transactionId;
    private String orderId;
    private String paymentStatus;
}
