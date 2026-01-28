package com.nivedita.transaction_system.event;

import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

        // ✅ SIMULATE SUCCESS / FAILURE (YOUR LOGIC)
        boolean success = Math.random() > 0.7; // force more failures

        if (success) {
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.saveAndFlush(transaction);
            log.info("✅ SUCCESS TX: {}", transaction.getId());
            return;
        }

        // ----- FAILURE FLOW -----
        int newRetry = transaction.getRetryCount() + 1;
        transaction.setRetryCount(newRetry);

        if (newRetry >= transaction.getMaxRetries()) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.saveAndFlush(transaction);
            log.error("❌ FAILED TX after retries: {}", transaction.getId());
            return;
        }

        transaction.setStatus(TransactionStatus.PENDING);
        transactionRepository.saveAndFlush(transaction);

        log.warn("🔁 RETRY {} for TX: {}", newRetry, transaction.getId());

        // 🔁 REPUBLISH EVENT FOR NEXT RETRY (YOUR CORE LOGIC)
        eventPublisher.publishEvent(
                new TransactionCreatedEvent(transaction.getId())
        );
    }
}
