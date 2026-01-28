package com.nivedita.transaction_system.service;

import com.nivedita.transaction_system.dto.response.TransactionResponse;
import com.nivedita.transaction_system.dto.response.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);

    TransactionResponse updateTransactionStatus(Long transactionId, String status);
}
