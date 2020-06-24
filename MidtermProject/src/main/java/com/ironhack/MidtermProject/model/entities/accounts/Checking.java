package com.ironhack.MidtermProject.model.entities.accounts;


import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    private Integer lastPenalty;
    @ElementCollection
    private List<LocalDateTime> transactionsMade;

    public Checking() {
        this.minimumBalance = minimumBalance == null ? new BigDecimal("250") : this.minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee == null ? new BigDecimal("12"): this.monthlyMaintenanceFee;
        this.accountType = AccountType.CHECKING;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    public Checking(Money balance, String secretKey, Status status, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance);
        this.secretKey = secretKey;
        this.status = status;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.accountType = AccountType.CHECKING;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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
}
