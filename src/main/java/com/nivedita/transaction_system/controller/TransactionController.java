package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.TransactionRequest;
import com.nivedita.transaction_system.dto.TransactionResponse;
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

    // POST - create transaction
    @PostMapping
    public Transaction createTransaction(
            @Valid @RequestBody TransactionRequest request
    ) {
        return service.createTransaction(request);
    }

    // ✅ GET - fetch transaction status
    @GetMapping("/{id}/status")
    public TransactionResponse getTransactionStatus(
            @PathVariable Long id
    ) {
        return service.getTransactionStatus(id);
    }
}
