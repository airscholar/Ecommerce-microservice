package com.airscholar.OrderService.query.api.projection;

import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.service.OrderService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderProjection {
    private OrderService orderService;

    public OrderProjection(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryHandler
    public Order getOrderById(String orderId){
        return orderService.getOrderByOrderId(orderId);
    }
}
