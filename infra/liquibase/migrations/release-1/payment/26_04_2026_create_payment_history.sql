CREATE TABLE payment_history
(
    id             UUID            PRIMARY KEY,
    external_id    UUID            NOT NULL,
    amount         NUMERIC(19, 2)  NOT NULL,
    currency_code  VARCHAR(3)      NOT NULL,
    currency_rate  NUMERIC(19, 6)  NOT NULL,
    currency_units INTEGER         NOT NULL,
    status_code    VARCHAR(20)     NOT NULL,
    description    TEXT,
    created_at     TIMESTAMP       NOT NULL,
    updated_at     TIMESTAMP       NOT NULL
);

CREATE INDEX idx_payment_history_created_at
    ON payment_history (created_at DESC);
