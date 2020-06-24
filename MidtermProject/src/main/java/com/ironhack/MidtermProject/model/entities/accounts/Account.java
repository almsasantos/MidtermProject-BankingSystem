package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer accountId;

    //@Digits(integer = 100, fraction = 4)
    @Embedded
    protected Money balance;

    //@Digits(integer = 6, fraction = 4)
    protected BigDecimal penaltyFee;

    @ManyToOne
    @JoinColumn(name = "primary_owner_id", referencedColumnName = "userId")
    protected AccountHolder primaryOwner;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id", referencedColumnName = "userId")
    protected AccountHolder secondaryOwner;

    @Enumerated(EnumType.STRING)
    protected AccountType accountType;

    protected Integer maxTransferencesInADay;

    public Account(){
        this.penaltyFee = new BigDecimal("40");
        this.maxTransferencesInADay = 1;
    }

    public Account(Money balance) {
        this.balance = balance;
        this.penaltyFee =  new BigDecimal("40");
        this.maxTransferencesInADay = 1;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Integer getMaxTransferencesInADay() {
        return maxTransferencesInADay;
    }

    public void setMaxTransferencesInADay(Integer maxTransferencesInADay) {
        this.maxTransferencesInADay = maxTransferencesInADay;
    }
}
