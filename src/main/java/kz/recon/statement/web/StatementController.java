package kz.recon.statement.web;

import jakarta.validation.Valid;
import kz.recon.statement.domain.BankTransaction;
import kz.recon.statement.service.StatementService;
import kz.recon.statement.web.dto.CreateTransactionRequest;
import kz.recon.statement.web.dto.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class StatementController {

    private final StatementService service;

    public StatementController(StatementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransactionRequest request) {
        BankTransaction tx = new BankTransaction();
        tx.setExternalId(request.externalId());
        tx.setAccountIban(request.accountIban());
        tx.setAmount(request.amount());
        tx.setCurrency(request.currency());
        tx.setDirection(request.direction());
        tx.setValueDate(request.valueDate());
        tx.setDescription(request.description());

        BankTransaction saved = service.importTransaction(tx);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(saved));
    }

    @GetMapping("/{id}")
    public TransactionResponse getOne(@PathVariable Long id) {
        return TransactionResponse.from(service.getById(id));
    }

    @GetMapping
    public List<TransactionResponse> list(@RequestParam String accountIban) {
        return service.findByAccount(accountIban).stream()
                .map(TransactionResponse::from)
                .toList();
    }

    @GetMapping("/balance")
    public double balance(@RequestParam String accountIban) {
        return service.calculateBalance(accountIban);
    }
}
