package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credit_card")
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {
    private BigDecimal creditLimit;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    private LocalDate date;
    private LocalDate lastInterestDate;

    public CreditCard() {
        this.creditLimit = creditLimit == null ? new BigDecimal("100") : this.creditLimit;
        this.interestRate = interestRate == null ? new BigDecimal("0.2") : this.interestRate;
        this.date = LocalDate.now();
        this.accountType = AccountType.CREDIT_CARD;
    }

    public CreditCard(Money balance, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.date = LocalDate.now();
        this.lastInterestDate = null;
        this.accountType = AccountType.CREDIT_CARD;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
