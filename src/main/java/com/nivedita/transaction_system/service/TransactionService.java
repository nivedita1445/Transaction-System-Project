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

    // 🔑 IDEMPOTENT CREATE
    public Transaction createTransaction(TransactionRequest requestDto, String idempotencyKey) {

        // 1️⃣ Check if transaction already exists
        return transactionRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {

                    // 2️⃣ Create new transaction
                    Transaction transaction = new Transaction();
                    transaction.setSenderAccount(requestDto.getSenderAccount());
                    transaction.setReceiverAccount(requestDto.getReceiverAccount());
                    transaction.setAmount(requestDto.getAmount());

                    transaction.setStatus(TransactionStatus.PENDING);
                    transaction.setIdempotencyKey(idempotencyKey);

                    // 3️⃣ Save
                    Transaction savedTransaction = transactionRepository.save(transaction);

                    // 4️⃣ Publish event
                    eventPublisher.publishEvent(
                            new TransactionCreatedEvent(savedTransaction.getId())
                    );

                    return savedTransaction;
                });
    }

    // Existing method (already implemented earlier)
    public TransactionStatus getTransactionStatus(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"))
                .getStatus();
    }
}
