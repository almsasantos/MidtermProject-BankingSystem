package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "savings")
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account {
    private BigDecimal interestRate;

    public Savings() {
    }

    public Savings(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, BigDecimal penaltyFee, Status status, BigDecimal interestRate) {
        super(accountId, balance, secretKey, primaryOwner,  penaltyFee, status);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}

