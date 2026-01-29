package com.nivedita.transaction_system.serviceImp1;

import com.nivedita.transaction_system.dto.request.TransactionRequest;
import com.nivedita.transaction_system.dto.response.TransactionResponse;
import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.event.TransactionCreatedEvent;
import com.nivedita.transaction_system.exception.DuplicateTransactionException;
import com.nivedita.transaction_system.exception.ResourceNotFoundException;
import com.nivedita.transaction_system.repository.TransactionRepository;
import com.nivedita.transaction_system.service.TransactionService;
import com.nivedita.transaction_system.exception.DuplicateTransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {

        log.info("➡️ Create Transaction request with idempotencyKey={}", request.getIdempotencyKey());

        // ✅ IDEMPOTENCY CHECK
        Transaction existing = transactionRepository
                .findByIdempotencyKey(request.getIdempotencyKey())
                .orElse(null);

        if (existing != null) {
            throw new DuplicateTransactionException(
                    "Duplicate transaction detected with idempotencyKey=" + request.getIdempotencyKey()
            );
        }


        // ✅ CREATE NEW TRANSACTION
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .senderAccount(request.getSenderAccount())
                .receiverAccount(request.getReceiverAccount())
                .description(request.getDescription())
                .idempotencyKey(request.getIdempotencyKey())
                .status(TransactionStatus.PENDING)
                .retryCount(0)
                .maxRetries(3)
                .build();

        Transaction saved = transactionRepository.save(transaction);

        log.info("✅ Transaction saved with ID={}", saved.getId());

        // ✅ EVENT PUBLISH
        eventPublisher.publishEvent(new TransactionCreatedEvent(saved.getId()));

        return mapToResponse(saved);
    }

    @Override
    public Page<TransactionResponse> getAllTransactions(int page, int size) {

        Page<Transaction> transactions = transactionRepository.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );

        return transactions.map(this::mapToResponse);
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {

        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id=" + id));

        return mapToResponse(tx);
    }

    private TransactionResponse mapToResponse(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getDescription(),
                tx.getStatus(),
                tx.getIdempotencyKey(),
                tx.getRetryCount(),
                tx.getCreatedAt()
        );
    }
}
