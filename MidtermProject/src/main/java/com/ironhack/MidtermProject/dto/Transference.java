package com.ironhack.MidtermProject.dto;

import java.math.BigDecimal;

public class Transference {
    private Integer userId;
    private Integer senderAccountId;
    private Integer receiverAccountId;
    private BigDecimal amount;

    public Transference() {}

    public Transference(Integer userId, Integer senderAccountId, Integer receiverAccountId, BigDecimal amount) {
        this.userId = userId;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Integer senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Integer getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Integer receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
