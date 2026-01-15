package com.spjartz.orderservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order entity")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID of the user who placed the order", example = "1")
    private Long userId;

    @Column(nullable = false)
    @Schema(description = "ID of the product ordered", example = "1")
    private Long productId;

    @Column(nullable = false)
    @Schema(description = "Quantity of the product ordered", example = "2")
    private Integer quantity;

    @Column(nullable = false)
    @Schema(description = "Total price of the order", example = "1999.98")
    private Double totalPrice;

    @Column(nullable = false)
    @Schema(description = "Status of the order", example = "PENDING")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    @Schema(description = "Date and time when the order was created")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }
}