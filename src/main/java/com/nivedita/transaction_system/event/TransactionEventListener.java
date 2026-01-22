package com.nivedita.transaction_system.event;

import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.repository.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionEventListener {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionEventListener(TransactionRepository transactionRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleTransactionCreated(TransactionCreatedEvent event) {

        Transaction transaction = transactionRepository.findById(event.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        boolean success = Math.random() > 0.7; // force more failures

        if (success) {
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.saveAndFlush(transaction);
            System.out.println("✅ SUCCESS TX: " + transaction.getId());
            return;
        }

        // ----- FAILURE -----
        int newRetry = transaction.getRetryCount() + 1;
        transaction.setRetryCount(newRetry);

        if (newRetry >= transaction.getMaxRetries()) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.saveAndFlush(transaction);
            System.out.println("❌ FAILED TX after retries: " + transaction.getId());
            return;
        }

        transaction.setStatus(TransactionStatus.PENDING);
        transactionRepository.saveAndFlush(transaction);

        System.out.println("🔁 RETRY " + newRetry + " for TX: " + transaction.getId());

        // 🔁 REPUBLISH EVENT FOR NEXT RETRY
        eventPublisher.publishEvent(
                new TransactionCreatedEvent(transaction.getId())
        );
    }
}
