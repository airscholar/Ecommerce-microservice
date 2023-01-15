package com.airscholar.OrderService.command.api.event;

import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.service.OrderService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    private OrderService orderService;

    public OrderEventHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @EventHandler
    public void handle(OrderCreatedEvent event){
        Order order = new Order();

        BeanUtils.copyProperties(event, order);

        orderService.create(order);
    }
}
