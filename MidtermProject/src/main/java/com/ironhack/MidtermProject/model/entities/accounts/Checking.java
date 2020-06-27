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

/**
 * Checking entity inherit from Account
 */
@Entity
@Table(name = "checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {
    /**
     * Checking secretKey, must have 6 integer digits
     */
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    /**
     * Checking status
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    /**
     * Checking minimumBalance
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    /**
     * Checking monthlyMaintenanceFee
     */
    private BigDecimal monthlyMaintenanceFee;
    /**
     * Checking lastPenalty
     */
    private Integer lastPenalty;
    @ElementCollection
    /**
     * Checking list of transactionsMade
     */
    private List<LocalDateTime> transactionsMade;

    /**
     * Void Constructor
     */
    public Checking() {
        this.minimumBalance = minimumBalance == null ? new BigDecimal("250") : this.minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee == null ? new BigDecimal("12"): this.monthlyMaintenanceFee;
        this.accountType = AccountType.CHECKING;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    /**
     * Constructor
     * @param balance Receives balance for checking's account
     * @param secretKey Receives secretKey for checking's account
     * @param status Receives status for checking's account
     * @param minimumBalance Receives minimumBalance for checking's account
     * @param monthlyMaintenanceFee Receives monthlyMaintenanceFee for checking's account
     */
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

    /**
     * Getter of Checking's minimumBalance
     * @return Checking's minimumBalance
     */
    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Setter of Checking's minimumBalance
     * @param minimumBalance Receives Checking's minimumBalance
     */
    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /**
     * Getter of Checking's monthlyMaintenanceFee
     * @return Checking's monthlyMaintenanceFee
     */
    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    /**
     * Setter of Checking's monthlyMaintenanceFee
     * @param monthlyMaintenanceFee Receives Checking's monthlyMaintenanceFee
     */
    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    /**
     * Getter of Checking's secretKey
     * @return Checking's secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of Checking's secretKey
     * @param secretKey Receives Checking's secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of Checking's status
     * @return Checking's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter of Checking's status
     * @param status Receives Checking's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of Checking's lastPenalty
     * @return Checking's lastPenalty
     */
    public Integer getLastPenalty() {
        return lastPenalty;
    }

    /**
     * Setter of Checking's lastPenalty
     * @param lastPenalty Receives Checking's lastPenalty
     */
    public void setLastPenalty(Integer lastPenalty) {
        this.lastPenalty = lastPenalty;
    }

    /**
     * Getter of Checking's transactionsMade
     * @return List of Checking's transactionsMade
     */
    public List<LocalDateTime> getTransactionsMade() {
        return transactionsMade;
    }

    /**
     * Setter of Checking's transactionsMade
     * @param transactionsMade Receives list of Checking's transactionsMade
     */
    public void setTransactionsMade(List<LocalDateTime> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }
}
