package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ChangeBalance {
    @NotNull(message = "Please insert an account id")
    private Integer accountId;
    @NotNull(message = "Please enter the owner id")
    private Integer ownerId;
    @NotNull(message = "Please insert the name of the owner's account")
    private String accountOwnerName;
    @NotNull(message = "Please insert the amount to transfer")
    private BigDecimal amount;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }
}
