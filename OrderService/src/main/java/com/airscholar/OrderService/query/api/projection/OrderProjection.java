package com.airscholar.OrderService.query.api.projection;

import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.service.OrderService;
import com.airscholar.OrderService.query.api.query.GetOrderByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderProjection {
    private OrderService orderService;

    public OrderProjection(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryHandler
    public Order getOrderById(GetOrderByIdQuery getOrderByIdQuery){
        return orderService.getOrderByOrderId(getOrderByIdQuery.getOrderId());
    }

}
