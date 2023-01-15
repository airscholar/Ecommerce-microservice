package com.airscholar.OrderService.command.api.aggregate;

import com.airscholar.OrderService.command.api.command.CreateOrderCommand;
import com.airscholar.OrderService.command.api.event.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@NoArgsConstructor
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String customerId;
    private String paymentId;
    private Integer quantity;
    private Double amount;
    private String status;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        log.info("Handling CreateOrderCommand {}", createOrderCommand);

        OrderCreatedEvent event = new OrderCreatedEvent();

        BeanUtils.copyProperties(createOrderCommand, event);

        apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.paymentId = event.getPaymentId();
        this.quantity = event.getQuantity();
        this.amount = event.getAmount();
        this.status = event.getStatus();
    }
}
