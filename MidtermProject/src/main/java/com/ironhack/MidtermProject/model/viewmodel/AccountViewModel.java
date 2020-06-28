package com.ironhack.MidtermProject.model.viewmodel;

import com.ironhack.MidtermProject.enums.Status;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * AccountViewModel is a class that allows Checking and StudentChecking entities to be created
 */
public class AccountViewModel {
    /**
     * AccountViewModel balance
     */
    private BigDecimal balance;

    /**
     * AccountViewModel secretKey, must have 6 integer numbers
     */
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;

    /**
     * AccountViewModel status
     */
    private Status status;

    /**
     * AccountViewModel primaryOwnerId
     */
    private Integer primaryOwnerId;

    /**
     * AccountViewModel secondaryOwnerId
     */
    private Integer secondaryOwnerId;

    /**
     * Getter of AccountViewModel's balance
     * @return AccountViewModel's balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Setter of AccountViewModel's balance
     * @param balance Receives AccountViewModel's balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Getter of AccountViewModel's secretKey
     * @return AccountViewModel's secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of AccountViewModel's secretKey
     * @param secretKey Receives AccountViewModel's secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of AccountViewModel's status
     * @return AccountViewModel's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter of AccountViewModel's status
     * @param status Receives enum AccountViewModel's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of AccountViewModel's primaryOwnerId
     * @return AccountViewModel's primaryOwnerId
     */
    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    /**
     * Setter of AccountViewModel's primaryOwnerId
     * @param primaryOwnerId Receives AccountViewModel's primaryOwnerId
     */
    public void setPrimaryOwnerId(Integer primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    /**
     * Getter of AccountViewModel's secondaryOwnerId
     * @return AccountViewModel's secondaryOwnerId
     */
    public Integer getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    /**
     * Setter of AccountViewModel's secondaryOwnerId
     * @param secondaryOwnerId Receives AccountViewModel's secondaryOwnerId
     */
    public void setSecondaryOwnerId(Integer secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }
}
