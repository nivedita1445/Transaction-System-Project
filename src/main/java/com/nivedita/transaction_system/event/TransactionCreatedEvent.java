package com.nivedita.transaction_system.event;

import com.nivedita.transaction_system.entity.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionCreatedEvent {

    private final Long transactionId;

    public TransactionCreatedEvent(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }
}

