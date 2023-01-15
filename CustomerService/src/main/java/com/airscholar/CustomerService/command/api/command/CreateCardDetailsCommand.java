package com.airscholar.CustomerService.command.api.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCardDetailsCommand {
    @TargetAggregateIdentifier
    private String cardId;
    private String customerId;
    private String cardName;
    private String cardPan;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cardCvv;
}
