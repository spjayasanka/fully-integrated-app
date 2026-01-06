package com.spjartz.productservice.service;

import com.spjartz.productservice.dto.ProductDTO;
import com.spjartz.productservice.entity.Product;
import com.spjartz.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
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
    @DisplayName("getAllProducts")
    class GetAllProductsTests {

        @Test
        @DisplayName("should return all products when products exist")
        void shouldReturnAllProducts() {
            Product product2 = new Product();
            product2.setId(2L);
            product2.setName("Product 2");
            product2.setPrice(149.99);

            when(productRepository.findAll()).thenReturn(Arrays.asList(product, product2));

            List<Product> result = productService.getAllProducts();

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getName()).isEqualTo("Test Product");
            assertThat(result.get(1).getName()).isEqualTo("Product 2");
            verify(productRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return empty list when no products exist")
        void shouldReturnEmptyListWhenNoProducts() {
            when(productRepository.findAll()).thenReturn(Collections.emptyList());

            List<Product> result = productService.getAllProducts();

            assertThat(result).isEmpty();
            verify(productRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("getProductById")
    class GetProductByIdTests {

        @Test
        @DisplayName("should return product when product exists")
        void shouldReturnProductWhenExists() {
            when(productRepository.findById(1L)).thenReturn(Optional.of(product));

            Optional<Product> result = productService.getProductById(1L);

            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(1L);
            assertThat(result.get().getName()).isEqualTo("Test Product");
            verify(productRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should return empty when product does not exist")
        void shouldReturnEmptyWhenProductNotFound() {
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            Optional<Product> result = productService.getProductById(999L);

            assertThat(result).isEmpty();
            verify(productRepository, times(1)).findById(999L);
        }
    }

    @Nested
    @DisplayName("createProduct")
    class CreateProductTests {

        @Test
        @DisplayName("should create and return product")
        void shouldCreateProduct() {
            when(productRepository.save(any(Product.class))).thenReturn(product);

            Product result = productService.createProduct(productDTO);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getName()).isEqualTo("Test Product");
            assertThat(result.getPrice()).isEqualTo(99.99);
            assertThat(result.getDescription()).isEqualTo("Test Description");
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        @DisplayName("should create product with null description")
        void shouldCreateProductWithNullDescription() {
            productDTO.setDescription(null);
            product.setDescription(null);
            when(productRepository.save(any(Product.class))).thenReturn(product);

            Product result = productService.createProduct(productDTO);

            assertThat(result).isNotNull();
            assertThat(result.getDescription()).isNull();
            verify(productRepository, times(1)).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("updateProduct")
    class UpdateProductTests {

        @Test
        @DisplayName("should update and return product when product exists")
        void shouldUpdateProductWhenExists() {
            ProductDTO updateDTO = new ProductDTO();
            updateDTO.setName("Updated Product");
            updateDTO.setPrice(199.99);
            updateDTO.setDescription("Updated Description");

            Product updatedProduct = new Product();
            updatedProduct.setId(1L);
            updatedProduct.setName("Updated Product");
            updatedProduct.setPrice(199.99);
            updatedProduct.setDescription("Updated Description");

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

            Optional<Product> result = productService.updateProduct(1L, updateDTO);

            assertThat(result).isPresent();
            assertThat(result.get().getName()).isEqualTo("Updated Product");
            assertThat(result.get().getPrice()).isEqualTo(199.99);
            assertThat(result.get().getDescription()).isEqualTo("Updated Description");
            verify(productRepository, times(1)).findById(1L);
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        @DisplayName("should return empty when product does not exist")
        void shouldReturnEmptyWhenProductNotFound() {
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            Optional<Product> result = productService.updateProduct(999L, productDTO);

            assertThat(result).isEmpty();
            verify(productRepository, times(1)).findById(999L);
            verify(productRepository, never()).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    class DeleteProductTests {

        @Test
        @DisplayName("should return true when product is deleted")
        void shouldReturnTrueWhenProductDeleted() {
            when(productRepository.existsById(1L)).thenReturn(true);
            doNothing().when(productRepository).deleteById(1L);

            boolean result = productService.deleteProduct(1L);

            assertThat(result).isTrue();
            verify(productRepository, times(1)).existsById(1L);
            verify(productRepository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("should return false when product does not exist")
        void shouldReturnFalseWhenProductNotFound() {
            when(productRepository.existsById(999L)).thenReturn(false);

            boolean result = productService.deleteProduct(999L);

            assertThat(result).isFalse();
            verify(productRepository, times(1)).existsById(999L);
            verify(productRepository, never()).deleteById(anyLong());
        }
    }
}