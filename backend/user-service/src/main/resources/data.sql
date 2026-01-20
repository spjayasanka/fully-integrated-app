-- Sample Users Data for Kafka Learning
-- This file will be executed by Spring Boot on startup

INSERT INTO users (username, email, first_name, last_name) VALUES
('john_doe', 'john.doe@email.com', 'John', 'Doe'),
('jane_smith', 'jane.smith@email.com', 'Jane', 'Smith'),
('bob_wilson', 'bob.wilson@email.com', 'Bob', 'Wilson'),
('alice_johnson', 'alice.johnson@email.com', 'Alice', 'Johnson'),
('charlie_brown', 'charlie.brown@email.com', 'Charlie', 'Brown'),
('diana_ross', 'diana.ross@email.com', 'Diana', 'Ross'),
('edward_norton', 'edward.norton@email.com', 'Edward', 'Norton'),
('fiona_apple', 'fiona.apple@email.com', 'Fiona', 'Apple'),
('george_lucas', 'george.lucas@email.com', 'George', 'Lucas'),
('helen_mirren', 'helen.mirren@email.com', 'Helen', 'Mirren')
ON CONFLICT (username) DO NOTHING;
