package com.ironhack.MidtermProject.controller.interfaces.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderControllerInterface {

    /**
     * Find all AccountHolder users created.
     * @return a list of account holder users.
     */
    public List<AccountHolder> findAll();

    /**
     * Find AccountHolder by id.
     * @param id receives an integer id of user.
     * @return an AccountHolder user corresponding to that id.
     */
    public AccountHolder findById(Integer id);

    /**
     * Allows AccountHolder users to check balance from their accounts.
     * @param accountId receives an integer with id from account.
     * @param userId receives an integer with id from account holder.
     * @return a BigDecimal with balance from account.
     */
    public BigDecimal checkAccountBalance(Integer accountId, Integer userId);

    /**
     * Allows AccountHolder users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged in.
     */
    public AccountHolder loginAccountHolder(LoginAccount loginAccount);

    /**
     * Allows AccountHolder users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged out.
     */
    public AccountHolder logoutAccountHolder(LoginAccount loginAccount);

    /**
     * Allows admins to create new account holders.
     * @param adminId receives an integer with id from admin.
     * @param accountHolders receives an AccountHolder with personal information needed to create that user.
     */
    public void createAccountHolder(Integer adminId, AccountHolder accountHolders);

    /**
     * Allows account holder users to transfer money from their accounts to others.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    public void transferAmount(Transference transference);
}
