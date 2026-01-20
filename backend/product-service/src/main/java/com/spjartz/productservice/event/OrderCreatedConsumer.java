package com.spjartz.productservice.event;

import com.spjartz.event.OrderCreatedEvent;
import com.spjartz.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderCreatedConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "order-created", groupId = "product-service")
    public void consume(OrderCreatedEvent event) {
        System.out.println("Order received: " + event);

        // Reduce product stock
         productService.reduceStock(event.getProductId(), event.getQuantity());
    }
}
