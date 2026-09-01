package kz.recon.statement.service;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Транзакция не найдена: id=" + id);
    }
}
