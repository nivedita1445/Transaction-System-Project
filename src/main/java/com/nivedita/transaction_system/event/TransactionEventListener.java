package com.nivedita.transaction_system.event;

import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(TransactionEventListener.class);

    private final TransactionRepository transactionRepository;

    public TransactionEventListener(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async
    @EventListener
    public void handleTransactionCreated(TransactionCreatedEvent event) {

        log.info("Started processing transaction id={}", event.getTransactionId());

        try {
            // Simulate processing delay (bank call, fraud check, etc.)
            Thread.sleep(5000);

            Optional<Transaction> optionalTransaction =
                    transactionRepository.findById(event.getTransactionId());

            if (optionalTransaction.isPresent()) {
                Transaction transaction = optionalTransaction.get();

                // Simulate realistic success / failure
                boolean isSuccess = Math.random() > 0.3; // 70% success

                if (isSuccess) {
                    transaction.setStatus(TransactionStatus.SUCCESS);
                } else {
                    transaction.setStatus(TransactionStatus.FAILED);
                }

                transactionRepository.save(transaction);

                log.info(
                        "Transaction id={} processed with status={}",
                        transaction.getId(),
                        transaction.getStatus()
                );
            }

        } catch (Exception e) {
            log.error(
                    "Error while processing transaction id={}",
                    event.getTransactionId(),
                    e
            );

            transactionRepository.findById(event.getTransactionId())
                    .ifPresent(transaction -> {
                        transaction.setStatus(TransactionStatus.FAILED);
                        transactionRepository.save(transaction);
                    });
        }
    }
}
