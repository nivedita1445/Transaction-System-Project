package com.nivedita.transaction_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = "idempotency_key")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 💰 Amount
    @Column(nullable = false)
    private Double amount;

    // 🏦 Sender Account
    @Column(name = "sender_account", nullable = false)
    private String senderAccount;

    // 🏦 Receiver Account
    @Column(name = "receiver_account", nullable = false)
    private String receiverAccount;

    // 📝 Description
    private String description;

    // ✅ Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    // ✅ Idempotency Key
    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    // 🔁 Retry Count
    @Column(nullable = false)
    private int retryCount;

    // 🔁 Max Retries
    @Column(nullable = false)
    private int maxRetries;
}
