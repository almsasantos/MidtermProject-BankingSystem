package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.service.accounts.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckingController {
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/checking_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAll(){
        return checkingService.findAll();
    }

    @GetMapping("/checking_accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@PathVariable("id") Integer id){
        return checkingService.findById(id);
    }
}
