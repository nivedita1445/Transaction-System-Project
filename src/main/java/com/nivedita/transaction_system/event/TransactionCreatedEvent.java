package com.nivedita.transaction_system.event;

public class TransactionCreatedEvent {

    private final Long transactionId;

    public TransactionCreatedEvent(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }
}
