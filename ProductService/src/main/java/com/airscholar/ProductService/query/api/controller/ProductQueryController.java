package com.airscholar.ProductService.query.api.controller;

import com.airscholar.ProductService.command.api.data.Product;
import com.airscholar.ProductService.query.api.query.GetAllProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<Product> products(){
        GetAllProductsQuery getAllProductsQuery = new GetAllProductsQuery();

        return queryGateway.query(getAllProductsQuery, ResponseTypes.multipleInstancesOf(Product.class)).join();
    }
}
