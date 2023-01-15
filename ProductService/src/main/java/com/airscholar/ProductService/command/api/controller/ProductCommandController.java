package com.airscholar.ProductService.command.api.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.airscholar.CommonService.command.CreateProductCommand;
import com.airscholar.ProductService.command.api.data.Product;
import com.airscholar.ProductService.command.api.data.ProductDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String create(@RequestBody ProductDTO productDTO){
        productDTO.setProductId(UUID.randomUUID().toString());

        CreateProductCommand createProductCommand = new CreateProductCommand();
        BeanUtils.copyProperties(productDTO, createProductCommand);

        commandGateway.sendAndWait(createProductCommand);

        return "Product created";
    }
}
