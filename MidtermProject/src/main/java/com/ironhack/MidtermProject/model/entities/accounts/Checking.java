package com.ironhack.MidtermProject.model.entities.accounts;


import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name = "checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {
    @Digits(integer = 6, fraction = 4)
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;

    public Checking() {
        this.minimumBalance = minimumBalance == null ? new BigDecimal("250") : this.minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee == null ? new BigDecimal("12"): this.monthlyMaintenanceFee;
        this.accountType = AccountType.CHECKING;
    }

    public Checking(Money balance, String secretKey, Status status, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, status);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.accountType = AccountType.CHECKING;
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
