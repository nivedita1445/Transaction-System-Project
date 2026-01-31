package com.nivedita.transaction_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
public class TransactionRequest {

    @NotNull(message = "Amount is required")
    @Schema(example = "1000", description = "Transaction amount")
    private Double amount;

    @NotBlank(message = "Sender account is required")
    private String senderAccount;

    @NotBlank(message = "Receiver account is required")
    private String receiverAccount;

    private String description;

    @NotBlank(message = "Idempotency key is required")
    private String idempotencyKey;
}
