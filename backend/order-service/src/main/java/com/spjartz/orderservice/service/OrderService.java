package com.spjartz.orderservice.service;

import com.spjartz.orderservice.dto.OrderDTO;
import com.spjartz.orderservice.entity.Order;
import com.spjartz.orderservice.entity.OrderStatus;
import com.spjartz.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Cacheable(value = "orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Cacheable(value = "order", key = "#id")
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Cacheable(value = "ordersByUser", key = "#userId")
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @CacheEvict(value = {"orders", "ordersByUser"}, allEntries = true)
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setProductId(orderDTO.getProductId());
        order.setQuantity(orderDTO.getQuantity());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @CacheEvict(value = {"orders", "ordersByUser"}, allEntries = true)
    public Optional<Order> updateOrder(Long id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setUserId(orderDTO.getUserId());
                    existingOrder.setProductId(orderDTO.getProductId());
                    existingOrder.setQuantity(orderDTO.getQuantity());
                    existingOrder.setTotalPrice(orderDTO.getTotalPrice());
                    if (orderDTO.getStatus() != null) {
                        existingOrder.setStatus(orderDTO.getStatus());
                    }
                    return orderRepository.save(existingOrder);
                });
    }

    @CacheEvict(value = {"orders", "order"}, allEntries = true)
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}