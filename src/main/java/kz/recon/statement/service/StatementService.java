package kz.recon.statement.service;

import kz.recon.statement.domain.BankTransaction;
import kz.recon.statement.domain.Direction;
import kz.recon.statement.repository.BankTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatementService {

    private static final Logger log = LoggerFactory.getLogger(StatementService.class);

    private final BankTransactionRepository repository;

    public StatementService(BankTransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public BankTransaction importTransaction(BankTransaction transaction) {
        log.debug("Импорт транзакции externalId={}", transaction.getExternalId());
        return repository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<BankTransaction> findByAccount(String accountIban) {
        return repository.findByAccountIban(accountIban);
    }

    @Transactional(readOnly = true)
    public BankTransaction getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    /**
     * Итоговое сальдо по счёту: приход минус расход.
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateBalance(String accountIban) {
        List<BankTransaction> transactions = repository.findByAccountIban(accountIban);
        BigDecimal balance = BigDecimal.ZERO;
        for (BankTransaction tx : transactions) {
            if (tx.getDirection() == Direction.CREDIT) {
                balance = balance.add(tx.getAmount());
            } else {
                balance = balance.subtract(tx.getAmount());
            }
        }
        return balance;
    }
}
