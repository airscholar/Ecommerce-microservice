package com.airscholar.OrderService.command.api.controller;

import com.airscholar.OrderService.command.api.command.CreateOrderCommand;
import com.airscholar.OrderService.command.api.data.OrderDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderDTO orderDTO){
        CreateOrderCommand createOrderCommand = new CreateOrderCommand();

        BeanUtils.copyProperties(orderDTO, createOrderCommand);

        createOrderCommand.setOrderId(UUID.randomUUID().toString());
        createOrderCommand.setStatus("CREATED");

        commandGateway.sendAndWait(createOrderCommand);
        return "Order Created";
    }
}
