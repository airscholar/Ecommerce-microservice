package com.airscholar.ProductService.command.api.services;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.airscholar.ProductService.command.api.data.Product;
import com.airscholar.ProductService.command.api.data.ProductDTO;
import com.airscholar.ProductService.command.api.data.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> products(){
        return productRepository.findAll();
    }

    public Product productById(Long productId){
        return productRepository.findById(productId).get();
    }

    public Product productByProductId(String productId){
        return productRepository.findByProductId(productId);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
}
