package kz.recon.statement.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kz.recon.statement.domain.Direction;
import java.math.BigDecimal;

import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotBlank String externalId,
        @NotBlank String accountIban,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency,
        @NotNull Direction direction,
        @NotNull LocalDate valueDate,
        String description
) {
}
