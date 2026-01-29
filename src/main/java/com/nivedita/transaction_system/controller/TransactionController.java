package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.request.TransactionRequest;
import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/transactions") // ✅ FIXED
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ApiResponse<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
        return new ApiResponse<>(
                true,
                "Transaction created successfully",
                transactionService.createTransaction(request)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ApiResponse<?> getTransactionById(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Transaction fetched successfully",
                transactionService.getTransactionById(id)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ApiResponse<?> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return new ApiResponse<>(
                true,
                "Transactions fetched successfully",
                transactionService.getAllTransactions(page, size)
        );
    }
}
