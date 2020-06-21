package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings")
@PrimaryKeyJoinColumn(name = "accountId")
public class Saving extends Account {
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    private LocalDate date;
    private LocalDate lastInterestDate;

    public Saving() {
        this.interestRate = interestRate == null ? new BigDecimal("0.0025") : this.interestRate;
        this.minimumBalance = minimumBalance == null ? new BigDecimal("1000") : this.minimumBalance;
        this.date = LocalDate.now();
    }

    public Saving(BigDecimal balance, String secretKey, Status status, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(balance, secretKey, status);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.date = LocalDate.now();
        this.lastInterestDate = null;
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

