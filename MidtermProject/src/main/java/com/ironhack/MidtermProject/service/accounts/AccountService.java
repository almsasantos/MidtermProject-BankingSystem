package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    /**
     * Find all Accounts created.
     * @return a list of all types of accounts.
     */
    public List<Account> findAll(){
        LOGGER.info("Get all Accounts");
        return accountRepository.findAll();
    }
}
