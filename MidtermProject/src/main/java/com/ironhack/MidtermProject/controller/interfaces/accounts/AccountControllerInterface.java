package com.ironhack.MidtermProject.controller.interfaces.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Account;

import java.util.List;

public interface AccountControllerInterface {

    /**
     * Find all Accounts created.
     * @return a list of all types of accounts.
     */
    public List<Account> findAll();
}
