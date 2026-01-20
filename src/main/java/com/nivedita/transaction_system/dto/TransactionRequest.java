package com.nivedita.transaction_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequest {

    @NotBlank(message = "Sender account must not be blank")
    private String senderAccount;

    @NotBlank(message = "Receiver account must not be blank")
    private String receiverAccount;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    // --- Getters and Setters ---

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
