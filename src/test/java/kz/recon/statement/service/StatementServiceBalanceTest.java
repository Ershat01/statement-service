package kz.recon.statement.service;

import kz.recon.statement.domain.BankTransaction;
import kz.recon.statement.domain.Direction;
import kz.recon.statement.repository.BankTransactionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TASK-2: этот тест сейчас падает. Разберись почему и исправь.
 */
class StatementServiceBalanceTest {

    private final BankTransactionRepository repository = mock(BankTransactionRepository.class);
    private final StatementService service = new StatementService(repository);

    @Test
    void balanceOfFullyOffsetAccountIsExactlyZero() {
        when(repository.findByAccountIban(anyString())).thenReturn(List.of(
                tx(Direction.CREDIT, 100.10),
                tx(Direction.CREDIT, 200.20),
                tx(Direction.CREDIT, 300.30),
                tx(Direction.DEBIT, 600.60)
        ));

        double balance = service.calculateBalance("KZ123456789012345678");

        assertThat(balance).isEqualTo(0.00);
    }

    @Test
    void balanceSumsManySmallCredits() {
        when(repository.findByAccountIban(anyString())).thenReturn(List.of(
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10),
                tx(Direction.CREDIT, 0.10)
        ));

        double balance = service.calculateBalance("KZ123456789012345678");

        assertThat(balance).isEqualTo(1.00);
    }

    private BankTransaction tx(Direction direction, double amount) {
        BankTransaction tx = new BankTransaction();
        tx.setExternalId("ext-" + amount + "-" + direction);
        tx.setAccountIban("KZ123456789012345678");
        tx.setAmount(amount);
        tx.setCurrency("KZT");
        tx.setDirection(direction);
        tx.setValueDate(LocalDate.of(2026, 1, 15));
        return tx;
    }
}
