package com.nivedita.transaction_system.repository;

import com.nivedita.transaction_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ IDEMPOTENCY SUPPORT
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    // ✅ PAGINATION SUPPORT (already provided by JpaRepository, but explicit for clarity)
    Page<Transaction> findAll(Pageable pageable);
}
