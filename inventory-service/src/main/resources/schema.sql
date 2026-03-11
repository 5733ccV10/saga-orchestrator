CREATE TABLE IF NOT EXISTS inventory(
    product_id VARCHAR(255) PRIMARY KEY,
    quantity INT NOT NULL CONSTRAINT chk_quantity CHECK (quantity >= 0),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS reservations(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL CONSTRAINT chk_reservation_quantity CHECK (quantity > 0),
    idempotency_key VARCHAR(255) UNIQUE NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES inventory(product_id)
);