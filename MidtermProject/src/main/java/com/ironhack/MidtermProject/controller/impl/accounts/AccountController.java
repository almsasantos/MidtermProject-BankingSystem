package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.controller.interfaces.accounts.AccountControllerInterface;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.service.accounts.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Account Controller")
@RestController
@RequestMapping("/")
public class AccountController implements AccountControllerInterface {

    @Autowired
    private AccountService accountService;

    /**
     * Find all Accounts created.
     * @return a list of all types of accounts.
     */
    @GetMapping("/accounts")
    @ApiOperation(value="Find All Accounts",
            notes = "Lists all accounts created",
            response = Account.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAll(){
        return accountService.findAll();
    }
}
