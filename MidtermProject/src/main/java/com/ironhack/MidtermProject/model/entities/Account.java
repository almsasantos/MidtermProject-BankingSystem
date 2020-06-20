package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long accountId;
    protected BigDecimal balance;
    protected String secretKey;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected AccountHolders primaryOwner;
    @ManyToOne
    @JoinColumn(name = "third_party_id", referencedColumnName = "id")
    protected ThirdParty secondaryOwner;
    protected BigDecimal penaltyFee;
    @Enumerated(EnumType.STRING)
    protected Status status;

    public Account() {
    }

    public Account(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, BigDecimal penaltyFee, Status status) {
        this.accountId = accountId;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = null;
        this.penaltyFee = penaltyFee;
        this.status = status;
    }

    public Account(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, ThirdParty secondaryOwner, BigDecimal penaltyFee, Status status) {
        this.accountId = accountId;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
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

    public AccountHolders getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolders primaryOwner) {
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
}
