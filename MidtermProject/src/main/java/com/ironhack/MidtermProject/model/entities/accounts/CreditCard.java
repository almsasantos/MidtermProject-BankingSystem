package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * CreditCard entity inherit from Account
 */
@Entity
@Table(name = "credit_card")
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {
    /**
     * CreditCard creditLimit
     */
    private BigDecimal creditLimit;
    /**
     * CreditCard interestRate
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;
    /**
     * CreditCard date of creation
     */
    private LocalDate date;
    /**
     * CreditCard lastInterestDate
     */
    private LocalDate lastInterestDate;

    /**
     * Void Constructor
     */
    public CreditCard() {
        this.creditLimit = creditLimit == null ? new BigDecimal("100") : this.creditLimit;
        this.interestRate = interestRate == null ? new BigDecimal("0.2") : this.interestRate;
        this.date = LocalDate.now();
        this.lastInterestDate = LocalDate.now();
        this.accountType = AccountType.CREDIT_CARD;
    }

    /**
     * Constructor
     * @param balance Receives balance for CreditCard's account
     * @param creditLimit Receives creditLimit for CreditCard's account
     * @param interestRate Receives interestRate for CreditCard's account
     */
    public CreditCard(Money balance, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.date = LocalDate.now();
        this.lastInterestDate = LocalDate.now();
        this.accountType = AccountType.CREDIT_CARD;
    }

    /**
     * Getter of CreditCard's creditLimit
     * @return CreditCard's creditLimit
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Setter of CreditCard's creditLimit
     * @param creditLimit Receives CreditCard's creditLimit
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Getter of CreditCard's interestRate
     * @return CreditCard's interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Setter of CreditCard's interestRate
     * @param interestRate Receives CreditCard's interestRate
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Getter of CreditCard's date of creation
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Getter of CreditCard's lastInterestDate
     * @return CreditCard's lastInterestDate
     */
    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    /**
     * Setter of CreditCard's lastInterestDate
     * @param lastInterestDate Receives CreditCard's lastInterestDate
     */
    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
