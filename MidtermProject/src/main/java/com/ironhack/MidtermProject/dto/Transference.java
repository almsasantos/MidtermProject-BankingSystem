package com.ironhack.MidtermProject.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Transference {
    private Integer userId;
    private String senderName;
    private Integer senderAccountId;
    private String receiverName;
    private Integer receiverAccountId;
    private BigDecimal amountToTransfer;

    public Transference() {}

    public Transference(Integer userId, String senderName, Integer senderAccountId, String receiverName, Integer receiverAccountId, BigDecimal amountToTransfer) {
        this.userId = userId;
        this.senderName = senderName;
        this.senderAccountId = senderAccountId;
        this.receiverName = receiverName;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "Transference{" +
                "userId=" + userId +
                ", senderName='" + senderName + '\'' +
                ", senderAccountId=" + senderAccountId +
                ", receiverName='" + receiverName + '\'' +
                ", receiverAccountId=" + receiverAccountId +
                ", amountToTransfer=" + amountToTransfer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transference that = (Transference) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(senderName, that.senderName) &&
                Objects.equals(senderAccountId, that.senderAccountId) &&
                Objects.equals(receiverName, that.receiverName) &&
                Objects.equals(receiverAccountId, that.receiverAccountId) &&
                Objects.equals(amountToTransfer, that.amountToTransfer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, senderName, senderAccountId, receiverName, receiverAccountId, amountToTransfer);
    }
}
