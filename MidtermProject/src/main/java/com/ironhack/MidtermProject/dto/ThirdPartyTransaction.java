package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ThirdPartyTransaction {
    @NotNull(message = "Please insert the account id to make transaction")
    private Integer accountId;
    @NotNull(message = "Please insert the secret key of respective account to make transaction")
    private String secretKey;
    @NotNull(message = "Please insert the amount to make transaction")
    private BigDecimal amount;

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
