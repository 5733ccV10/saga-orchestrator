CREATE TABLE IF NOT EXISTS shipments (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key VARCHAR(255)    NOT NULL UNIQUE,
    order_id        VARCHAR(255)    NOT NULL,
    reservation_id  VARCHAR(255)    NOT NULL,
    tracking_number VARCHAR(100),
    status          VARCHAR(20)     NOT NULL,
    address         VARCHAR(500)    NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);
