package com.spjartz.productservice.controller;

import com.spjartz.productservice.dto.ProductDTO;
import com.spjartz.productservice.entity.Product;
import com.spjartz.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found and returned successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found with the given ID",
                    content = @Content)
    })
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content)
    })
    public ResponseEntity<Product> createProduct(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product", description = "Update a product by its ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found with the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content)
    })
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated product details", required = true)
            @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the given ID",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}