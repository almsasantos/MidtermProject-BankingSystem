package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

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
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    protected String secretKey;

    //@Digits(integer = 6, fraction = 4)
    protected BigDecimal penaltyFee;

    @Enumerated(EnumType.STRING)
    protected Status status;

    @ManyToOne
    @JoinColumn(name = "primary_owner_id", referencedColumnName = "userId")
    protected AccountHolder primaryOwner;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id", referencedColumnName = "userId")
    protected AccountHolder secondaryOwner;

    @Enumerated(EnumType.STRING)
    protected AccountType accountType;

    public Account(){
        this.penaltyFee = new BigDecimal("40");
    }

    public Account(Money balance, String secretKey, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.penaltyFee =  new BigDecimal("40");
        this.status = status;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", secretKey='" + secretKey + '\'' +
                ", penaltyFee=" + penaltyFee +
                ", status=" + status +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                ", accountType=" + accountType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(secretKey, account.secretKey) &&
                Objects.equals(penaltyFee, account.penaltyFee) &&
                status == account.status &&
                Objects.equals(primaryOwner, account.primaryOwner) &&
                Objects.equals(secondaryOwner, account.secondaryOwner) &&
                accountType == account.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance, secretKey, penaltyFee, status, primaryOwner, secondaryOwner, accountType);
    }
}
