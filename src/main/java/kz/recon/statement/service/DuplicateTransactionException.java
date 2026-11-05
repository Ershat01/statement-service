package kz.recon.statement.service;

public class DuplicateTransactionException extends RuntimeException {

    public DuplicateTransactionException(String externalId) {
        super("Транзакция уже импортирована: externalId=" + externalId);
    }
}