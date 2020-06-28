package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ChangeBalance Dto that allows to change amount of Accounts.
 */
public class ChangeBalance {
    /**
     * ChangeBalance's accountId, receives id of account to change balance.
     */
    @NotNull(message = "Please insert an account id")
    private Integer accountId;

    /**
     * ChangeBalance's ownerId, receives id of primary or secondary owner of that account.
     */
    @NotNull(message = "Please enter the owner id")
    private Integer ownerId;

    /**
     * ChangeBalance's accountOwnerName, receives the name of primary or secondary owner of that account.
     */
    @NotNull(message = "Please insert the name of the owner's account")
    private String accountOwnerName;

    /**
     * ChangeBalance's amount, receives the amount that will change the balance of that account.
     */
    @NotNull(message = "Please insert the amount to transfer")
    private BigDecimal amount;

    /**
     * Getter of ChangeBalance's accountId.
     * @return an integer with accountId.
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Setter of ChangeBalance's accountId.
     * @param accountId receives an integer of accountId.
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter of ChangeBalance's ownerId.
     * @return an integer with ownerId.
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * Setter of ChangeBalance's ownerId.
     * @param ownerId receives an integer of ownerId.
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Getter of ChangeBalance's amount.
     * @return a BigDecimal with amount to change in that account.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Setter of ChangeBalance's amount.
     * @param amount receives a BigDecimal with amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Getter of ChangeBalance's accountOwnerName.
     * @return a string with name of account owner.
     */
    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    /**
     * Setter of ChangeBalance's accountOwnerName.
     * @param accountOwnerName receives a string with name of account owner.
     */
    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }
}
