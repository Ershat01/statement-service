package kz.recon.statement.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.recon.statement.domain.Direction;

import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotBlank String externalId,
        @NotBlank String accountIban,
        @NotNull Double amount,
        @NotBlank String currency,
        @NotNull Direction direction,
        @NotNull LocalDate valueDate,
        String description
) {
}
