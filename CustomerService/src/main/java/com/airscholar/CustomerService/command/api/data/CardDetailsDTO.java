package com.airscholar.CustomerService.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsDTO {
    private String customerId;
    private String cardName;
    private String cardPan;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cardCvv;
}
