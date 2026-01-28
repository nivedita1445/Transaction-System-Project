package com.nivedita.transaction_system.dto.request;

import lombok.Data;

@Data
public class TransactionRequest {

    private Double amount;
    private String senderAccount;
    private String receiverAccount;
    private String description;
    private String idempotencyKey;
}
