package com.airscholar.CommonService.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ValidatePaymentCommand {
    @TargetAggregateIdentifier
    private String transactionId;
    private String orderId;
    private String paymentStatus;
}
