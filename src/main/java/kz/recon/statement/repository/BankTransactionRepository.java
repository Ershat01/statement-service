package kz.recon.statement.repository;

import kz.recon.statement.domain.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByAccountIban(String accountIban);

    Optional<BankTransaction> findByExternalId(String externalId);
}
