package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "credit_card")
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account{
    private BigDecimal creditLimit;
    private BigDecimal interestRate;
    private BigDecimal penaltyFee;

    public CreditCard() {}

    public CreditCard(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, BigDecimal penaltyFee, Status status, BigDecimal creditLimit, BigDecimal interestRate, BigDecimal penaltyFee1) {
        super(accountId, balance, secretKey, primaryOwner, penaltyFee, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.penaltyFee = penaltyFee1;
    }

    public CreditCard(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, ThirdParty secondaryOwner, BigDecimal penaltyFee, Status status, BigDecimal creditLimit, BigDecimal interestRate, BigDecimal penaltyFee1) {
        super(accountId, balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.penaltyFee = penaltyFee1;
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

    @Override
    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    @Override
    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }
}
