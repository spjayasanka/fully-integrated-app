-- Sample Orders Data for Kafka Learning
-- This file will be executed by Spring Boot on startup
-- Note: userId references users table (1-10), productId references products table (1-15)

INSERT INTO orders (user_id, product_id, quantity, total_price, status, created_at) VALUES
(1, 2, 1, 89.99, 'CONFIRMED', CURRENT_TIMESTAMP - INTERVAL '5 days'),
(2, 6, 1, 199.99, 'SHIPPED', CURRENT_TIMESTAMP - INTERVAL '3 days'),
(3, 1, 2, 59.98, 'PENDING', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(1, 8, 1, 299.99, 'DELIVERED', CURRENT_TIMESTAMP - INTERVAL '10 days'),
(4, 11, 3, 74.97, 'PENDING', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
