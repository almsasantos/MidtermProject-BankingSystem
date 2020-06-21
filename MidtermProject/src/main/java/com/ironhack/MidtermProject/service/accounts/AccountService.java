package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.model.entities.Account;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll(){
        return accountRepository.findAll();
    }
}
