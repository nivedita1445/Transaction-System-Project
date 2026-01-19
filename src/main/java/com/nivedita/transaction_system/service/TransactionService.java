package com.nivedita.transaction_system.service;

import com.nivedita.transaction_system.dto.TransactionRequest;
import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.event.TransactionCreatedEvent;
import com.nivedita.transaction_system.repository.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionService(TransactionRepository transactionRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    public Transaction createTransaction(TransactionRequest requestDto) {

        // 1. Create entity
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(requestDto.getSenderAccount());
        transaction.setReceiverAccount(requestDto.getReceiverAccount());
        transaction.setAmount(requestDto.getAmount());

        // 2. Backend-controlled status
        transaction.setStatus(TransactionStatus.PENDING);

        // 3. Save to DB
        Transaction savedTransaction = transactionRepository.save(transaction);

        // 4. Publish domain event (event-driven architecture)
        eventPublisher.publishEvent(
                new TransactionCreatedEvent(savedTransaction.getId())
        );

        // 5. Return immediately (async processing happens later)
        return savedTransaction;
    }
}
