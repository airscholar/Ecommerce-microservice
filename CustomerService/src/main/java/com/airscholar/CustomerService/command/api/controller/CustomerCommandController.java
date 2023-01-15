package com.airscholar.CustomerService.command.api.controller;

import com.airscholar.CustomerService.command.api.command.CreateCustomerCommand;
import com.airscholar.CustomerService.command.api.data.CustomerDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerCommandController {

    private CommandGateway commandGateway;

    public CustomerCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerDTO customerDTO){
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand();
        BeanUtils.copyProperties(customerDTO, createCustomerCommand);

        createCustomerCommand.setCustomerId(UUID.randomUUID().toString());

        commandGateway.sendAndWait(createCustomerCommand);

        return "Account created";
    }

}
