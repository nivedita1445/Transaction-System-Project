package com.nivedita.transaction_system.service;

import com.nivedita.transaction_system.dto.request.TransactionRequest;
import com.nivedita.transaction_system.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest request);

    Page<TransactionResponse> getAllTransactions(int page, int size);

    TransactionResponse getTransactionById(Long id);
}
