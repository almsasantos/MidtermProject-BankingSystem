package com.ironhack.MidtermProject.model.viewmodel;

import com.ironhack.MidtermProject.enums.Status;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class SavingViewModel {
    private BigDecimal balance;
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    private Status status;
    private Integer primaryOwnerId;
    private Integer secondaryOwnerId;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Integer primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Integer getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Integer secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
