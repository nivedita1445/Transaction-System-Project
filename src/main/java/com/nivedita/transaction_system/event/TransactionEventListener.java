package com.nivedita.transaction_system.event;

import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.repository.TransactionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionEventListener {

    private final TransactionRepository transactionRepository;

    public TransactionEventListener(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async
    @EventListener
    public void handleTransactionCreated(TransactionCreatedEvent event) {

        try {
            // Simulate processing time (real systems do validations, bank calls, etc.)
            Thread.sleep(5000);

            Optional<Transaction> optionalTransaction =
                    transactionRepository.findById(event.getTransactionId());

            if (optionalTransaction.isPresent()) {
                Transaction transaction = optionalTransaction.get();

                // Simulate success (later we can add failure logic)
                transaction.setStatus(TransactionStatus.SUCCESS);
                transactionRepository.save(transaction);
            }

        } catch (Exception e) {
            // In real systems, we log & mark FAILED
            transactionRepository.findById(event.getTransactionId())
                    .ifPresent(transaction -> {
                        transaction.setStatus(TransactionStatus.FAILED);
                        transactionRepository.save(transaction);
                    });
        }
    }
}
