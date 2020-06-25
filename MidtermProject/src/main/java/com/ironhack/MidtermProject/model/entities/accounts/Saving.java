package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "savings")
@PrimaryKeyJoinColumn(name = "accountId")
public class Saving extends Account {
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    private LocalDate date;
    private LocalDate lastInterestDate;
    private Integer lastPenalty;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> transactionsMade;

    public Saving() {
        this.interestRate = interestRate == null ? new BigDecimal("0.0025") : this.interestRate;
        this.minimumBalance = minimumBalance == null ? new BigDecimal("1000") : this.minimumBalance;
        this.date = LocalDate.now();
        this.lastInterestDate = LocalDate.now();
        this.accountType = AccountType.SAVINGS;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    public Saving(Money balance, String secretKey, Status status, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(balance);
        this.secretKey = secretKey;
        this.status = status;
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.date = LocalDate.now();
        this.lastInterestDate = LocalDate.now();
        this.accountType = AccountType.SAVINGS;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
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

    public Integer getLastPenalty() {
        return lastPenalty;
    }

    public void setLastPenalty(Integer lastPenalty) {
        this.lastPenalty = lastPenalty;
    }

    public List<LocalDateTime> getTransactionsMade() {
        return transactionsMade;
    }

    public void setTransactionsMade(List<LocalDateTime> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }

    @Override
    public String toString() {
        return "Saving{" +
                "secretKey='" + secretKey + '\'' +
                ", status=" + status +
                ", interestRate=" + interestRate +
                ", minimumBalance=" + minimumBalance +
                ", date=" + date +
                ", lastInterestDate=" + lastInterestDate +
                ", lastPenalty=" + lastPenalty +
                ", transactionsMade=" + transactionsMade +
                '}';
    }
}

