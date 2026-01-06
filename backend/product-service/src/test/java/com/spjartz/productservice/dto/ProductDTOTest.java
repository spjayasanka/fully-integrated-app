package com.spjartz.productservice.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("should pass validation with valid data")
        void shouldPassValidationWithValidData() {
            ProductDTO dto = new ProductDTO();
            dto.setName("Test Product");
            dto.setPrice(99.99);
            dto.setDescription("Test Description");

            Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("should fail validation when name is null")
        void shouldFailValidationWhenNameIsNull() {
            ProductDTO dto = new ProductDTO();
            dto.setName(null);
            dto.setPrice(99.99);

            Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("name");
        }

        @Test
        @DisplayName("should fail validation when price is null")
        void shouldFailValidationWhenPriceIsNull() {
            ProductDTO dto = new ProductDTO();
            dto.setName("Test Product");
            dto.setPrice(null);

            Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("price");
        }

        @Test
        @DisplayName("should fail validation when name and price are null")
        void shouldFailValidationWhenNameAndPriceAreNull() {
            ProductDTO dto = new ProductDTO();
            dto.setName(null);
            dto.setPrice(null);

            Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

            assertThat(violations).hasSize(2);
        }

        @Test
        @DisplayName("should pass validation when description is null")
        void shouldPassValidationWhenDescriptionIsNull() {
            ProductDTO dto = new ProductDTO();
            dto.setName("Test Product");
            dto.setPrice(99.99);
            dto.setDescription(null);

            Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("should get and set id")
        void shouldGetAndSetId() {
            ProductDTO dto = new ProductDTO();
            dto.setId(1L);

            assertThat(dto.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("should get and set name")
        void shouldGetAndSetName() {
            ProductDTO dto = new ProductDTO();
            dto.setName("Test Product");

            assertThat(dto.getName()).isEqualTo("Test Product");
        }

        @Test
        @DisplayName("should get and set price")
        void shouldGetAndSetPrice() {
            ProductDTO dto = new ProductDTO();
            dto.setPrice(99.99);

            assertThat(dto.getPrice()).isEqualTo(99.99);
        }

        @Test
        @DisplayName("should get and set description")
        void shouldGetAndSetDescription() {
            ProductDTO dto = new ProductDTO();
            dto.setDescription("Test Description");

            assertThat(dto.getDescription()).isEqualTo("Test Description");
        }
    }
}