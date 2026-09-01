CREATE TABLE bank_transaction (
    id            BIGSERIAL PRIMARY KEY,
    external_id   VARCHAR(64)    NOT NULL,
    account_iban  VARCHAR(34)    NOT NULL,
    amount        NUMERIC(19, 2) NOT NULL,
    currency      VARCHAR(3)     NOT NULL,
    direction     VARCHAR(10)    NOT NULL,
    value_date    DATE           NOT NULL,
    description   VARCHAR(500),
    status        VARCHAR(20)    NOT NULL,
    created_at    TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE INDEX idx_bank_transaction_account ON bank_transaction (account_iban);
