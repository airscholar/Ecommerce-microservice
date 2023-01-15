package com.airscholar.ProductService.command.api.events;

import com.airscholar.ProductService.command.api.data.Product;
import com.airscholar.ProductService.command.api.services.ProductService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandEventHandler {
    private ProductService productService;
    public ProductCommandEventHandler(ProductService productService) {
        this.productService = productService;
    }

    @EventHandler
    public void handle(ProductCreatedEvent event){
        Product product = new Product();
        BeanUtils.copyProperties(event, product);

        this.productService.createProduct(product);
    }
}
