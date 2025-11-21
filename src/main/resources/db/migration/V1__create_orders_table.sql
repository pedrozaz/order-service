CREATE TABLE orders (
    id UUID PRIMARY KEY,
    customer_wallet_id UUID NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    payment_transaction_id VARCHAR(255)
);