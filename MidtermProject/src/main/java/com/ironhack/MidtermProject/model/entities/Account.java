package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer accountId;

    //@Digits(integer = 100, fraction = 4)
    protected BigDecimal balance;
    protected String secretKey;

    //@Digits(integer = 6, fraction = 4)
    protected BigDecimal penaltyFee;

    @Enumerated(EnumType.STRING)
    protected Status status;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "userId")
    protected AccountHolder primaryOwner;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "third_party_id", referencedColumnName = "userId")
    protected ThirdParty secondaryOwner;

    public Account(){
        this.penaltyFee = new BigDecimal("40");
    }

    public Account(BigDecimal balance, String secretKey, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.penaltyFee = new BigDecimal("40");
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public ThirdParty getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(ThirdParty secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
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

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", secretKey='" + secretKey + '\'' +
                ", primaryOwner=" + primaryOwner +
                ", penaltyFee=" + penaltyFee +
                ", status=" + status +
                '}';
    }
}
