package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.service.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAll(){
        return accountService.findAll();
    }
}
