package com.ironhack.MidtermProject.model.viewmodel;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class CreditCardViewModel {
    private BigDecimal balance;
    private Integer primaryOwnerId;
    private Integer secondaryOwnerId;
    private BigDecimal creditLimit;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;

    public CreditCardViewModel() {
        this.creditLimit = null;
        this.interestRate = null;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
