package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.service.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountHolderController {
    @Autowired
    private AccountHolderService accountsHolderService;

    @GetMapping("/accountholders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAll(){
        return accountsHolderService.findAll();
    }

    @GetMapping("/accountholders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder findById(@PathVariable("id") Integer id){
        return accountsHolderService.findById(id);
    }

    @GetMapping("/accountholders/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkAccountBalance(@RequestParam(value = "account_id", required = true) Integer accountId, @RequestParam(value = "user_id", required = true)  Integer userId){
        return accountsHolderService.checkAccountBalance(accountId, userId);
    }

    @PostMapping("/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccountHolder(@RequestBody AccountHolder accountHolders){
        accountsHolderService.createAccountHolder(accountHolders);
    }

    @PostMapping("/transference")
    @ResponseStatus(HttpStatus.CREATED)
    public void transferAmount(@RequestBody Transference transference) throws Exception{
        accountsHolderService.transferAmount(transference);
    }

}
