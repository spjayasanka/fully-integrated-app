package com.spjartz.productservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product entity")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Price of the product", example = "999.99")
    private Double price;

    @Schema(description = "Description of the product", example = "High-performance laptop with 16GB RAM")
    private String description;
}