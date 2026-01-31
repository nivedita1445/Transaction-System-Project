package com.nivedita.transaction_system.controller;

import com.nivedita.transaction_system.dto.request.TransactionRequest;
import com.nivedita.transaction_system.dto.response.ApiResponse;
import com.nivedita.transaction_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/transactions") // ✅ FIXED
@Tag(name = "Transaction API", description = "Operations related to transactions")

@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Get all transactions", description = "Fetch all transactions from database")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ApiResponse<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
        return new ApiResponse<>(
                true,
                "Transaction created successfully",
                transactionService.createTransaction(request)
        );
    }

    @Operation(summary = "Get all transactions", description = "Fetch all transactions from database")
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
