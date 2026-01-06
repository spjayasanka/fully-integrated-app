package com.spjartz.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spjartz.productservice.dto.ProductDTO;
import com.spjartz.productservice.entity.Product;
import com.spjartz.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private ProductService productService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setDescription("Test Description");

        productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(99.99);
        productDTO.setDescription("Test Description");
    }

    @Nested
    @DisplayName("GET /api/products")
    class GetAllProductsTests {

        @Test
        @DisplayName("should return all products with status 200")
        void shouldReturnAllProducts() throws Exception {
            Product product2 = new Product();
            product2.setId(2L);
            product2.setName("Product 2");
            product2.setPrice(149.99);
            product2.setDescription("Description 2");

            when(productService.getAllProducts()).thenReturn(Arrays.asList(product, product2));

            mockMvc.perform(get("/api/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("Test Product")))
                    .andExpect(jsonPath("$[1].id", is(2)))
                    .andExpect(jsonPath("$[1].name", is("Product 2")));

            verify(productService, times(1)).getAllProducts();
        }

        @Test
        @DisplayName("should return empty list with status 200 when no products")
        void shouldReturnEmptyList() throws Exception {
            when(productService.getAllProducts()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(0)));

            verify(productService, times(1)).getAllProducts();
        }
    }

    @Nested
    @DisplayName("GET /api/products/{id}")
    class GetProductByIdTests {

        @Test
        @DisplayName("should return product with status 200 when product exists")
        void shouldReturnProductWhenExists() throws Exception {
            when(productService.getProductById(1L)).thenReturn(Optional.of(product));

            mockMvc.perform(get("/api/products/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Test Product")))
                    .andExpect(jsonPath("$.price", is(99.99)))
                    .andExpect(jsonPath("$.description", is("Test Description")));

            verify(productService, times(1)).getProductById(1L);
        }

        @Test
        @DisplayName("should return 404 when product does not exist")
        void shouldReturn404WhenProductNotFound() throws Exception {
            when(productService.getProductById(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/products/999"))
                    .andExpect(status().isNotFound());

            verify(productService, times(1)).getProductById(999L);
        }
    }

    @Nested
    @DisplayName("POST /api/products")
    class CreateProductTests {

        @Test
        @DisplayName("should create product and return status 201")
        void shouldCreateProduct() throws Exception {
            when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Test Product")))
                    .andExpect(jsonPath("$.price", is(99.99)))
                    .andExpect(jsonPath("$.description", is("Test Description")));

            verify(productService, times(1)).createProduct(any(ProductDTO.class));
        }

        @Test
        @DisplayName("should create product without description")
        void shouldCreateProductWithoutDescription() throws Exception {
            productDTO.setDescription(null);
            product.setDescription(null);
            when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.description").doesNotExist());

            verify(productService, times(1)).createProduct(any(ProductDTO.class));
        }
    }

    @Nested
    @DisplayName("PUT /api/products/{id}")
    class UpdateProductTests {

        @Test
        @DisplayName("should update product and return status 200")
        void shouldUpdateProduct() throws Exception {
            ProductDTO updateDTO = new ProductDTO();
            updateDTO.setName("Updated Product");
            updateDTO.setPrice(199.99);
            updateDTO.setDescription("Updated Description");

            Product updatedProduct = new Product();
            updatedProduct.setId(1L);
            updatedProduct.setName("Updated Product");
            updatedProduct.setPrice(199.99);
            updatedProduct.setDescription("Updated Description");

            when(productService.updateProduct(eq(1L), any(ProductDTO.class)))
                    .thenReturn(Optional.of(updatedProduct));

            mockMvc.perform(put("/api/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Updated Product")))
                    .andExpect(jsonPath("$.price", is(199.99)))
                    .andExpect(jsonPath("$.description", is("Updated Description")));

            verify(productService, times(1)).updateProduct(eq(1L), any(ProductDTO.class));
        }

        @Test
        @DisplayName("should return 404 when updating non-existent product")
        void shouldReturn404WhenProductNotFound() throws Exception {
            when(productService.updateProduct(eq(999L), any(ProductDTO.class)))
                    .thenReturn(Optional.empty());

            mockMvc.perform(put("/api/products/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productDTO)))
                    .andExpect(status().isNotFound());

            verify(productService, times(1)).updateProduct(eq(999L), any(ProductDTO.class));
        }
    }

    @Nested
    @DisplayName("DELETE /api/products/{id}")
    class DeleteProductTests {

        @Test
        @DisplayName("should delete product and return status 204")
        void shouldDeleteProduct() throws Exception {
            when(productService.deleteProduct(1L)).thenReturn(true);

            mockMvc.perform(delete("/api/products/1"))
                    .andExpect(status().isNoContent());

            verify(productService, times(1)).deleteProduct(1L);
        }

        @Test
        @DisplayName("should return 404 when deleting non-existent product")
        void shouldReturn404WhenProductNotFound() throws Exception {
            when(productService.deleteProduct(999L)).thenReturn(false);

            mockMvc.perform(delete("/api/products/999"))
                    .andExpect(status().isNotFound());

            verify(productService, times(1)).deleteProduct(999L);
        }
    }
}