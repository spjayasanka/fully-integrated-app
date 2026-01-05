package com.spjartz.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Product Data Transfer Object")
public class ProductDTO {

    @Schema(description = "Product ID (ignored for create operations)", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Laptop", required = true)
    private String name;

    @Schema(description = "Price of the product", example = "999.99", required = true)
    private Double price;

    @Schema(description = "Description of the product", example = "High-performance laptop with 16GB RAM")
    private String description;
}