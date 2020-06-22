package com.ironhack.MidtermProject.dto;

import java.math.BigDecimal;

public class ThirdPartyTransaction {
    private Integer accountId;
    private String secretKey;
    private BigDecimal amount;

    public ThirdPartyTransaction() {}

    public ThirdPartyTransaction(Integer accountId, String secretKey, BigDecimal amount) {
        this.accountId = accountId;
        this.secretKey = secretKey;
        this.amount = amount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
