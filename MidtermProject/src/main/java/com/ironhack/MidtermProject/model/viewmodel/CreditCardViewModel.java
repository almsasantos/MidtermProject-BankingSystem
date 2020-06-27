package com.ironhack.MidtermProject.model.viewmodel;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * CreditCardViewModel is a class that allows CreditCard entities to be created
 */
public class CreditCardViewModel {
    /**
     * CreditCardViewModel balance
     */
    private BigDecimal balance;

    /**
     * CreditCardViewModel primaryOwnerId
     */
    private Integer primaryOwnerId;

    /**
     * CreditCardViewModel secondaryOwnerId
     */
    private Integer secondaryOwnerId;

    /**
     * CreditCardViewModel creditLimit
     */
    private BigDecimal creditLimit;

    /**
     * CreditCardViewModel interestRate
     */
    @Digits(integer = 6, fraction = 4)
    private BigDecimal interestRate;

    /**
     * Void Constructor
     */
    public CreditCardViewModel() {
        this.creditLimit = null;
        this.interestRate = null;
    }

    /**
     * Getter of CreditCardViewModel's balance
     * @return CreditCardViewModel's balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Setter of CreditCardViewModel's balance
     * @param balance Receives CreditCardViewModel's balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Getter of CreditCardViewModel's primaryOwnerId
     * @return CreditCardViewModel's primaryOwnerId
     */
    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    /**
     * Setter of CreditCardViewModel's primaryOwnerId
     * @param primaryOwnerId Receives CreditCardViewModel's primaryOwnerId
     */
    public void setPrimaryOwnerId(Integer primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    /**
     * Getter of CreditCardViewModel's secondaryOwnerId
     * @return CreditCardViewModel's secondaryOwnerId
     */
    public Integer getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    /**
     * Setter of CreditCardViewModel's secondaryOwnerId
     * @param secondaryOwnerId Receives CreditCardViewModel's secondaryOwnerId
     */
    public void setSecondaryOwnerId(Integer secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    /**
     * Getter of CreditCardViewModel's creditLimit
     * @return CreditCardViewModel's creditLimit
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Setter of CreditCardViewModel's creditLimit
     * @param creditLimit Receives CreditCardViewModel's creditLimit
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Getter of CreditCardViewModel's interestRate
     * @return CreditCardViewModel's interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Setter of CreditCardViewModel's interestRate
     * @param interestRate Receives CreditCardViewModel's interestRate
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
