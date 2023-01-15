package com.airscholar.ProductService.command.api.aggregate;

import com.airscholar.CommonService.command.CreateProductCommand;
import com.airscholar.ProductService.command.api.events.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Component
@Aggregate
@AllArgsConstructor
@NoArgsConstructor
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private Long quantity;
    private Double price;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        this.productId = event.getProductId();
        this.name = event.getName();
        this.quantity = event.getQuantity();
        this.price = event.getPrice();
    }
}
