package com.airscholar.OrderService.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order getOrderByOrderId(String orderId);
}
