package com.ironhack.MidtermProject.dto;

import java.math.BigDecimal;

public class ChangeBalance {
    private Integer accountId;
    private String accountPrimaryOwnerName;
    private BigDecimal amount;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountPrimaryOwnerName() {
        return accountPrimaryOwnerName;
    }

    public void setAccountPrimaryOwnerName(String accountPrimaryOwnerName) {
        this.accountPrimaryOwnerName = accountPrimaryOwnerName;
    }
}
