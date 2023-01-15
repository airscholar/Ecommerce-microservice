package com.airscholar.CommonService.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String name;
    private Long quantity;
    private Double price;
}
