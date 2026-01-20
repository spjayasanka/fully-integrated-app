package com.spjartz.event;

import java.time.Instant;

public class OrderCreatedEvent {

    private Long orderId;
    private Long productId;
    private Double amount;
    private Instant createdAt;

    private Integer quantity;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, Long productId, Double amount, Instant createdAt, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
