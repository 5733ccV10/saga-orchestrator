CREATE TABLE IF NOT EXISTS transactions (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key VARCHAR(255)    NOT NULL UNIQUE,
    order_id        VARCHAR(255)    NOT NULL,
    customer_id     VARCHAR(255)    NOT NULL,
    amount          NUMERIC(12,2)   NOT NULL,
    type            VARCHAR(10)     NOT NULL,
    status          VARCHAR(20)     NOT NULL,
    related_txn_id  UUID,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);
