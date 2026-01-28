package com.nivedita.transaction_system.serviceImp1;

import com.nivedita.transaction_system.dto.response.TransactionResponse;
import com.nivedita.transaction_system.dto.response.UserResponse;
import com.nivedita.transaction_system.entity.Transaction;
import com.nivedita.transaction_system.entity.TransactionStatus;
import com.nivedita.transaction_system.entity.User;
import com.nivedita.transaction_system.exception.ResourceNotFoundException;
import com.nivedita.transaction_system.repository.TransactionRepository;
import com.nivedita.transaction_system.repository.UserRepository;
import com.nivedita.transaction_system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id=" + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public TransactionResponse updateTransactionStatus(Long transactionId, String status) {

        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        tx.setStatus(TransactionStatus.valueOf(status));

        Transaction updated = transactionRepository.save(tx);

        return new TransactionResponse(
                updated.getId(),
                updated.getAmount(),
                updated.getDescription(),
                updated.getStatus(),
                updated.getIdempotencyKey(),
                updated.getRetryCount(),
                updated.getCreatedAt()
        );
    }
}
