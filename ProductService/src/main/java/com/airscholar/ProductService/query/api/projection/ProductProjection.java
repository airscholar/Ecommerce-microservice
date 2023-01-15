package com.airscholar.ProductService.query.api.projection;

import com.airscholar.ProductService.command.api.data.Product;
import com.airscholar.ProductService.command.api.services.ProductService;
import com.airscholar.ProductService.query.api.query.GetAllProductsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductProjection {

    private ProductService productService;

    public ProductProjection(ProductService productService) {
        this.productService = productService;
    }

    @QueryHandler
    public List<Product> handle(GetAllProductsQuery getAllProductsQuery){
        return productService.products();
    }
}
