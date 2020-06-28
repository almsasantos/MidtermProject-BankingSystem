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

/**
 * Savings entity inherit from Account
 */
@Entity
@Table(name = "savings")
@PrimaryKeyJoinColumn(name = "accountId")
public class Saving extends Account {
    /**
     * Saving secretKey, must have 6 integer digits
     */
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    /**
     * Saving status
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    /**
     * Saving interestRate
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    /**
     * Saving minimum balance
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    /**
     * Saving date of account's creation
     */
    private LocalDate date;
    /**
     * Saving date of last interest deposit
     */
    private LocalDate lastInterestDate;
    /**
     * Saving last penalty fee
     */
    private Integer lastPenalty;
    /**
     * Saving list of transactionsMade
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> transactionsMade;

    /**
     * Void Constructor
     */
    public Saving() {
        this.interestRate = interestRate == null ? new BigDecimal("0.0025") : this.interestRate;
        this.minimumBalance = minimumBalance == null ? new BigDecimal("1000") : this.minimumBalance;
        this.date = LocalDate.now();
        this.lastInterestDate = LocalDate.now();
        this.accountType = AccountType.SAVINGS;
        this.lastPenalty = 0;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    /**
     * Constructor
     * @param balance Receives balance for Savings account
     * @param secretKey Receives secretKey for Savings account
     * @param status Receives status for Savings account
     * @param interestRate Receives interestRate for Savings account
     * @param minimumBalance Receives minimumBalance for Savings account
     */
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

    /**
     * Getter of Saving's interestRate
     * @return Saving's interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Setter of Saving's interestRate
     * @param interestRate Receives Saving's interestRate
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Getter of Saving's minimumBalance
     * @return Saving's minimumBalance
     */
    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Setter of Saving's minimumBalance
     * @param minimumBalance Receives Saving's minimumBalance
     */
    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /**
     * Getter of Saving's Date of creation
     * @return Saving's Date of creation
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Getter of Saving's lastInterestRate
     * @return Saving's lastInterestRate
     */
    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    /**
     * Setter of Saving's lastInterestRate
     * @param lastInterestDate Receives Saving's lastInterestRate
     */
    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }

    /**
     * Getter of Saving's secretKey
     * @return Saving's secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of Saving's secretKey
     * @param secretKey Receives Saving's secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of Saving's status
     * @return Saving's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter of Saving's status
     * @param status Receives Saving's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of Saving's lastPenalty
     * @return Saving's lastPenalty
     */
    public Integer getLastPenalty() {
        return lastPenalty;
    }

    /**
     * Setter of Saving's lastPenalty
     * @param lastPenalty Receives Saving's lastPenalty
     */
    public void setLastPenalty(Integer lastPenalty) {
        this.lastPenalty = lastPenalty;
    }

    /**
     * Getter of Saving's transactionsMade
     * @return List of Saving's transactionsMade
     */
    public List<LocalDateTime> getTransactionsMade() {
        return transactionsMade;
    }

    /**
     * Setter of Saving's transactionsMade
     * @param transactionsMade Receives list of Saving's transactionsMade
     */
    public void setTransactionsMade(List<LocalDateTime> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }

    /**
     * Representation of Savings in form of String
     * @return Savings in form of String
     */
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

