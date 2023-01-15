package com.airscholar.OrderService.command.api.service;

import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.data.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderByOrderId(String orderId) {
        return orderRepository.getOrderByOrderId(orderId);
    }
}
