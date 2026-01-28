package com.nivedita.transaction_system.dto.response;

import com.nivedita.transaction_system.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private Double amount;
    private String description;
    private TransactionStatus status;

    private String idempotencyKey;   // ✅ for debugging & tracking
    private int retryCount;          // ✅ retry feature

    private LocalDateTime createdAt;
}
