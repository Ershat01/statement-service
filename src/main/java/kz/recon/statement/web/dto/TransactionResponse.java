package kz.recon.statement.web.dto;

import kz.recon.statement.domain.BankTransaction;
import kz.recon.statement.domain.Direction;
import kz.recon.statement.domain.TransactionStatus;

import java.time.LocalDate;
import java.math.BigDecimal;

public record TransactionResponse(
        Long id,
        String externalId,
        String accountIban,
        BigDecimal amount,
        String currency,
        Direction direction,
        LocalDate valueDate,
        String description,
        TransactionStatus status
) {
    public static TransactionResponse from(BankTransaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getExternalId(),
                tx.getAccountIban(),
                tx.getAmount(),
                tx.getCurrency(),
                tx.getDirection(),
                tx.getValueDate(),
                tx.getDescription(),
                tx.getStatus()
        );
    }
}
