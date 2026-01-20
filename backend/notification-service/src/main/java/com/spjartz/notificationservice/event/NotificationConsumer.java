package com.spjartz.notificationservice.event;

import com.spjartz.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-created", groupId = "notification-service")
    public void notify(OrderCreatedEvent event) {
        System.out.println("📧 Sending email for Order ID: " + event.getOrderId());
    }
}
