package com.nivedita.transaction_system.dto;

import com.nivedita.transaction_system.entity.TransactionStatus;

public class TransactionResponse {

    private Long transactionId;
    private TransactionStatus status;

    // Constructor
    public TransactionResponse(Long transactionId, TransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getter
    public Long getTransactionId() {
        return transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }
}
