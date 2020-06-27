package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Transference class allows AccountHolder entity to make transference between own accounts and others.
 */
public class Transference {
    /**
     * Transference userId, refers to the id from the User who wants to make the transference.
     */
    @NotNull(message = "Please insert your user id")
    private Integer userId;

    /**
     * Transference senderName, refers to the name from the User who wants to make the transference.
     */
    @NotNull(message = "Please insert your name")
    private String senderName;

    /**
     * Transference senderAccountId, refers to the id from the Account which is making the transference.
     */
    @NotNull(message = "Please insert your account id that you want to transfer from")
    private Integer senderAccountId;

    /**
     * Transference receiverName, refers to the name from the User who is receiving.
     */
    @NotNull(message = "Please insert the name of person you want to transfer the amount to")
    private String receiverName;

    /**
     * Transference receiverAccountId, refers to the id from the Account which is receiving the transference.
     */
    @NotNull(message = "Please insert the account id that you want to transfer to")
    private Integer receiverAccountId;

    /**
     * Transference amountToTransfer, refers to the amount involved in the transference.
     */
    @NotNull(message = "Please insert the amount to transfer")
    private BigDecimal amountToTransfer;

    /**
     * Transference Void Constructor
     */
    public Transference() {}

    /**
     * Transference constructor
     * @param userId Receives the id from User who is making the transference.
     * @param senderName Receives the name from User who is making the transference.
     * @param senderAccountId Receives the id from Account who is making the transference.
     * @param receiverName Receives the name from User who is receiving the transference.
     * @param receiverAccountId Receives the id from Account who is receiving the transference.
     * @param amountToTransfer Receives the amount involved in the transference.
     */
    public Transference(Integer userId, String senderName, Integer senderAccountId, String receiverName, Integer receiverAccountId, BigDecimal amountToTransfer) {
        this.userId = userId;
        this.senderName = senderName;
        this.senderAccountId = senderAccountId;
        this.receiverName = receiverName;
        this.receiverAccountId = receiverAccountId;
        this.amountToTransfer = amountToTransfer;
    }

    /**
     * Getter of Transference userId.
     * @return an integer with the id from User who is making the transference.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Setter of Transference userId.
     * @param userId receives the if from User who is making the transference.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Getter of Transference senderAccountId.
     * @return an integer with the id from Account who is making the transference.
     */
    public Integer getSenderAccountId() {
        return senderAccountId;
    }

    /**
     * Setter of Transference senderAccountId.
     * @param senderAccountId receives an integer of id from Account who is making the transference.
     */
    public void setSenderAccountId(Integer senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    /**
     * Getter of Transference receiverAccountId.
     * @return an integer with id from Account who is receiving the transference.
     */
    public Integer getReceiverAccountId() {
        return receiverAccountId;
    }

    /**
     * Setter of Transference receiverAccountId.
     * @param receiverAccountId receives an integer with id from Account who is receiving the transference.
     */
    public void setReceiverAccountId(Integer receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    /**
     * Getter of Transference amountToTransfer.
     * @return a BigDecimal with amount to transfer.
     */
    public BigDecimal getAmountToTransfer() {
        return amountToTransfer;
    }

    /**
     * Setter of Transference amountToTransfer.
     * @param amountToTransfer receives a BigDecimal of amount to transfer.
     */
    public void setAmountToTransfer(BigDecimal amountToTransfer) {
        this.amountToTransfer = amountToTransfer;
    }

    /**
     * Getter of Transference senderName.
     * @return a string with name of User who is making the transference.
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Setter of Transference senderName.
     * @param senderName receives a string with name of User who is making the transference.
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * Getter of Transference receiverName.
     * @return a string with name of account owner who is receiving the transference.
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * Setter of Transference receiverName.
     * @param receiverName receives a string with name of account owner who is receiving the transference.
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * Representation of Transference in a form of String.
     * @return a string of transference.
     */
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

    /**
     * Check if two or more transferences are equal between with other.
     * @param o receives an object.
     * @return a boolean value.
     */
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

    /**
     * Transference hashCode.
     * @return an integer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, senderName, senderAccountId, receiverName, receiverAccountId, amountToTransfer);
    }
}
