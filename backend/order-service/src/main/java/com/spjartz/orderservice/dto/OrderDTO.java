package com.spjartz.orderservice.dto;

import com.spjartz.orderservice.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Order Data Transfer Object")
public class OrderDTO {

    @Schema(description = "Order ID (ignored for create operations)", example = "1")
    private Long id;

    @Schema(description = "ID of the user placing the order", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "ID of the product being ordered", example = "1")
    @NotNull
    private Long productId;

    @Schema(description = "Quantity of the product", example = "2")
    @NotNull
    @Positive
    private Integer quantity;

    @Schema(description = "Total price of the order", example = "1999.98")
    @NotNull
    @Positive
    private Double totalPrice;

    @Schema(description = "Status of the order", example = "PENDING")
    private OrderStatus status;
}