-- Sample Products Data for Kafka Learning
-- This file will be executed by Spring Boot on startup

INSERT INTO products (name, price, description) VALUES
('Wireless Mouse', 29.99, 'Ergonomic wireless mouse with USB receiver'),
('Mechanical Keyboard', 89.99, 'RGB backlit mechanical keyboard with Cherry MX switches'),
('USB-C Hub', 49.99, '7-in-1 USB-C hub with HDMI, USB 3.0, and SD card reader'),
('Laptop Stand', 39.99, 'Adjustable aluminum laptop stand for better ergonomics'),
('Webcam HD', 79.99, '1080p HD webcam with built-in microphone'),
('Noise Cancelling Headphones', 199.99, 'Over-ear headphones with active noise cancellation'),
('Portable SSD 1TB', 129.99, 'Fast portable SSD with USB 3.2 interface'),
('Monitor 27 inch', 299.99, '27 inch 4K IPS monitor with HDR support'),
('Desk Lamp LED', 34.99, 'Adjustable LED desk lamp with multiple brightness levels'),
('Cable Management Kit', 19.99, 'Complete kit for organizing desk cables'),
('Wireless Charger', 24.99, '15W fast wireless charging pad'),
('Bluetooth Speaker', 59.99, 'Portable Bluetooth speaker with 12-hour battery'),
('Gaming Mouse Pad', 14.99, 'Large extended mouse pad with stitched edges'),
('Phone Stand', 12.99, 'Adjustable aluminum phone and tablet stand'),
('Screen Cleaner Kit', 9.99, 'Microfiber cloth and cleaning solution for screens')
ON CONFLICT DO NOTHING;
