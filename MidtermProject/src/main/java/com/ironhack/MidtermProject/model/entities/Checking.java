package com.ironhack.MidtermProject.model.entities;


import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account{
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;

    public Checking() {
    }

    public Checking(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, BigDecimal penaltyFee, Status status, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(accountId, balance, secretKey, primaryOwner, penaltyFee, status);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public Checking(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, ThirdParty secondaryOwner, BigDecimal penaltyFee, Status status, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(accountId, balance, secretKey, primaryOwner,secondaryOwner, penaltyFee, status);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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

}
