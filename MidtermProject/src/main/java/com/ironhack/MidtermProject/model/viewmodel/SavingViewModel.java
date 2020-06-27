package com.ironhack.MidtermProject.model.viewmodel;

import com.ironhack.MidtermProject.enums.Status;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * SavingViewModel is a class that allows Saving entities to be created
 */
public class SavingViewModel {
    /**
     * SavingViewModel balance
     */
    private BigDecimal balance;

    /**
     * SavingViewModel secretKey, must have 6 integer numbers
     */
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;

    /**
     * SavingViewModel status
     */
    private Status status;

    /**
     * SavingViewModel primaryOwnerId
     */
    private Integer primaryOwnerId;

    /**
     * SavingViewModel secondaryOwnerId
     */
    private Integer secondaryOwnerId;

    /**
     * SavingViewModel interestRate
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;

    /**
     * SavingViewModel minimumBalance
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;

    /**
     * Void Constructor
     */
    public SavingViewModel() {
        this.interestRate = null;
        this.minimumBalance = null;
    }

    /**
     * Getter of SavingViewModel's balance
     * @return SavingViewModel's balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Setter of SavingViewModel's balance
     * @param balance Receives SavingViewModel's balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Getter of SavingViewModel's secretKey
     * @return SavingViewModel's secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of SavingViewModel's secretKey
     * @param secretKey Receives SavingViewModel's secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of SavingViewModel's status
     * @return SavingViewModel's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter of SavingViewModel's status
     * @param status Receives enum SavingViewModel's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of SavingViewModel's primaryOwnerId
     * @return SavingViewModel's primaryOwnerId
     */
    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    /**
     * Setter of SavingViewModel's primaryOwnerId
     * @param primaryOwnerId Receives SavingViewModel's primaryOwnerId
     */
    public void setPrimaryOwnerId(Integer primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    /**
     * Getter of SavingViewModel's secondaryOwnerId
     * @return SavingViewModel's secondaryOwnerId
     */
    public Integer getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    /**
     * Setter of SavingViewModel's secondaryOwnerId
     * @param secondaryOwnerId Receives SavingViewModel's secondaryOwnerId
     */
    public void setSecondaryOwnerId(Integer secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    /**
     * Getter of SavingViewModel's interestRate
     * @return SavingViewModel's interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Setter of SavingViewModel's interestRate
     * @param interestRate Receives SavingViewModel's interestRate
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Getter of SavingViewModel's minimumBalance
     * @return SavingViewModel's minimumBalance
     */
    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Setter of SavingViewModel's minimumBalance
     * @param minimumBalance Receives SavingViewModel's minimumBalance
     */
    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
