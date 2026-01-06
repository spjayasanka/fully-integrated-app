package com.spjartz.productservice.repository;

import com.spjartz.productservice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setDescription("Test Description");
    }

    @Nested
    @DisplayName("save")
    class SaveTests {

        @Test
        @DisplayName("should save and return product with generated id")
        void shouldSaveProduct() {
            Product savedProduct = productRepository.save(product);

            assertThat(savedProduct).isNotNull();
            assertThat(savedProduct.getId()).isNotNull();
            assertThat(savedProduct.getName()).isEqualTo("Test Product");
            assertThat(savedProduct.getPrice()).isEqualTo(99.99);
            assertThat(savedProduct.getDescription()).isEqualTo("Test Description");
        }

        @Test
        @DisplayName("should save product with null description")
        void shouldSaveProductWithNullDescription() {
            product.setDescription(null);

            Product savedProduct = productRepository.save(product);

            assertThat(savedProduct).isNotNull();
            assertThat(savedProduct.getId()).isNotNull();
            assertThat(savedProduct.getDescription()).isNull();
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("should find product by id")
        void shouldFindProductById() {
            Product savedProduct = productRepository.save(product);

            Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

            assertThat(foundProduct).isPresent();
            assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        }

        @Test
        @DisplayName("should return empty when product not found")
        void shouldReturnEmptyWhenNotFound() {
            Optional<Product> foundProduct = productRepository.findById(999L);

            assertThat(foundProduct).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("should find all products")
        void shouldFindAllProducts() {
            Product product2 = new Product();
            product2.setName("Product 2");
            product2.setPrice(149.99);
            product2.setDescription("Description 2");

            productRepository.save(product);
            productRepository.save(product2);

            List<Product> products = productRepository.findAll();

            assertThat(products).hasSize(2);
        }

        @Test
        @DisplayName("should return empty list when no products")
        void shouldReturnEmptyListWhenNoProducts() {
            List<Product> products = productRepository.findAll();

            assertThat(products).isEmpty();
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteByIdTests {

        @Test
        @DisplayName("should delete product by id")
        void shouldDeleteProductById() {
            Product savedProduct = productRepository.save(product);
            Long productId = savedProduct.getId();

            productRepository.deleteById(productId);

            Optional<Product> deletedProduct = productRepository.findById(productId);
            assertThat(deletedProduct).isEmpty();
        }
    }

    @Nested
    @DisplayName("existsById")
    class ExistsByIdTests {

        @Test
        @DisplayName("should return true when product exists")
        void shouldReturnTrueWhenExists() {
            Product savedProduct = productRepository.save(product);

            boolean exists = productRepository.existsById(savedProduct.getId());

            assertThat(exists).isTrue();
        }

        @Test
        @DisplayName("should return false when product does not exist")
        void shouldReturnFalseWhenNotExists() {
            boolean exists = productRepository.existsById(999L);

            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTests {

        @Test
        @DisplayName("should update existing product")
        void shouldUpdateProduct() {
            Product savedProduct = productRepository.save(product);

            savedProduct.setName("Updated Product");
            savedProduct.setPrice(199.99);
            savedProduct.setDescription("Updated Description");

            Product updatedProduct = productRepository.save(savedProduct);

            assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
            assertThat(updatedProduct.getPrice()).isEqualTo(199.99);
            assertThat(updatedProduct.getDescription()).isEqualTo("Updated Description");
        }
    }
}