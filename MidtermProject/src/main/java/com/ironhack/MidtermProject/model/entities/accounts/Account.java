package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Account entity that has general information for all the other accounts
 */
@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    /**
     * Account's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer accountId;

    /**
     * Account's balance
     */
    @Embedded
    protected Money balance;

    /**
     * Account's penaltyFee
     */
    protected BigDecimal penaltyFee;

    /**
     * Account's primaryOwner
     */
    @ManyToOne
    @JoinColumn(name = "primary_owner_id", referencedColumnName = "userId")
    protected AccountHolder primaryOwner;

    /**
     * Account's secondaryOwner
     */
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id", referencedColumnName = "userId")
    protected AccountHolder secondaryOwner;

    /**
     * Account's accountType
     */
    @Enumerated(EnumType.STRING)
    protected AccountType accountType;

    /**
     * Account's maxTransferencesInADay
     */
    protected Integer maxTransferencesInADay;

    /**
     * Void Constructor
     */
    public Account(){
        this.penaltyFee = new BigDecimal("40");
        this.maxTransferencesInADay = 2;
    }

    /**
     * Constructor
     * @param balance Receives Account's balance
     */
    public Account(Money balance) {
        this.balance = balance;
        this.penaltyFee =  new BigDecimal("40");
        this.maxTransferencesInADay = 2;
    }

    /**
     * Getter of Account's id
     * @return Account's id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Setter of Account's id
     * @param accountId Receives Account's id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter of Account's balance
     * @return Account's balance
     */
    public Money getBalance() {
        return balance;
    }

    /**
     * Setter of Account's balance
     * @param balance Receives Account's balance
     */
    public void setBalance(Money balance) {
        this.balance = balance;
    }

    /**
     * Getter of Account's penaltyFee
     * @return Account's penaltyFee
     */
    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    /**
     * Setter of Account's penaltyFee
     * @param penaltyFee Receives Account's penaltyFee
     */
    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    /**
     * Getter of Account's accountType
     * @return Account's accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter of Account's accountType
     * @param accountType Receives enum of Account's accountType
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter of Account's primaryOwner
     * @return Account's primaryOwner which is an AccountHolder
     */
    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    /**
     * Setter of Account's primaryOwner
     * @param primaryOwner Receives Account's primaryOwner which is an AccountHolder
     */
    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    /**
     * Getter of Account's secondaryOwner
     * @return Account's secondaryOwner which is an AccountHolder
     */
    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    /**
     * Setter of Account's secondaryOwner
     * @param secondaryOwner Receives Account's secondaryOwner which is an AccountHolder
     */
    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    /**
     * Getter of Account's maxTransferencesInADay
     * @return Account's maxTransferencesInADay
     */
    public Integer getMaxTransferencesInADay() {
        return maxTransferencesInADay;
    }

    /**
     * Setter of Account's maxTransferencesInADay
     * @param maxTransferencesInADay Receives Account's maxTransferencesInADay
     */
    public void setMaxTransferencesInADay(Integer maxTransferencesInADay) {
        this.maxTransferencesInADay = maxTransferencesInADay;
    }
}
