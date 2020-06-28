package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ThirdPartyTransaction allows ThirdParty users to make credit and debit transaction to and from any account.
 */
public class ThirdPartyTransaction {
    /**
     * ThirdPartyTransaction accountId, must correspond to the account involved on the transaction.
     */
    @NotNull(message = "Please insert the account id to make transaction")
    private Integer accountId;

    /**
     * ThirdPartyTransaction secretKey, must correspond to the account involved on the transaction.
     */
    @NotNull(message = "Please insert the secret key of respective account to make transaction")
    private String secretKey;

    /**
     * ThirdPartyTransaction amount corresponds to the amount to put or take from the account specified.
     */
    @NotNull(message = "Please insert the amount to make transaction")
    private BigDecimal amount;

    /**
     * Getter of ThirdPartyTransaction's accountId.
     * @return an integer with id of account.
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Setter of ThirdPartyTransaction's accountId.
     * @param accountId receives an integer with id of account.
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter of ThirdPartyTransaction's secretKey.
     * @return a string with the secretkey from the account specified.
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of ThirdPartyTransaction's secretKey.
     * @param secretKey receives a string of secretKey.
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of ThirdPartyTransaction's amount.
     * @return a BigDecimal with the amount to put or take from that account.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Setter of ThirdPartyTransaction's amount.
     * @param amount receives a BigDecimal of amount to put or take from that account.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
