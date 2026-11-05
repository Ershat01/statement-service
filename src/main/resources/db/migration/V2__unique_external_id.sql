ALTER TABLE bank_transaction
    ADD CONSTRAINT uq_bank_transaction_external_id UNIQUE (external_id);