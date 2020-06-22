package com.ironhack.MidtermProject.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Transference {
    private Integer userId;
    private String senderFirstName;
    private Integer senderAccountId;
    private String receiverFirstName;
    private Integer receiverAccountId;
    private BigDecimal amountToTransfer;

    public Transference() {}

    public Transference(Integer userId, String senderFirstName, Integer senderAccountId, String receiverFirstName, Integer receiverAccountId, BigDecimal amountToTransfer) {
        this.userId = userId;
        this.senderFirstName = senderFirstName;
        this.senderAccountId = senderAccountId;
        this.receiverFirstName = receiverFirstName;
        this.receiverAccountId = receiverAccountId;
        this.amountToTransfer = amountToTransfer;
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

    public BigDecimal getAmountToTransfer() {
        return amountToTransfer;
    }

    public void setAmountToTransfer(BigDecimal amountToTransfer) {
        this.amountToTransfer = amountToTransfer;
    }

    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    public void setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName = receiverFirstName;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transference that = (Transference) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(senderFirstName, that.senderFirstName) &&
                Objects.equals(senderAccountId, that.senderAccountId) &&
                Objects.equals(receiverFirstName, that.receiverFirstName) &&
                Objects.equals(receiverAccountId, that.receiverAccountId) &&
                Objects.equals(amountToTransfer, that.amountToTransfer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, senderFirstName, senderAccountId, receiverFirstName, receiverAccountId, amountToTransfer);
    }
}
