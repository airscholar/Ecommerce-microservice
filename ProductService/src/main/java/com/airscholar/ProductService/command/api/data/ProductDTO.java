package com.airscholar.ProductService.command.api.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private String productId;
    private String name;
    private Long quantity;
    private Double price;
}
