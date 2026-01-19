package com.nivedita.transaction_system.service;

import com.nivedita.transaction_system.dto.TransactionRequest;
import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction createTransaction(TransactionRequest request) {
        Transaction tx = new Transaction();
        tx.setSenderAccount(request.getSenderAccount());
        tx.setReceiverAccount(request.getReceiverAccount());
        tx.setAmount(request.getAmount());

        return repository.save(tx);
    }
}
