package com.nivedita.transaction_system.repository;

import com.nivedita.transaction_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
