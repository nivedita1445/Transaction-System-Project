package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.TransactionRequest;
import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction createTransaction(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody TransactionRequest request
    ) {
        return service.createTransaction(request, idempotencyKey);
    }

    @GetMapping("/{id}/status")
    public String getStatus(@PathVariable Long id) {
        return service.getTransactionStatus(id).name();
    }
}
