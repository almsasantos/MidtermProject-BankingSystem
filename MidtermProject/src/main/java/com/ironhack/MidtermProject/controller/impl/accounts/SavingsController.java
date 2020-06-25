package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class SavingsController {
    @Autowired
    private SavingsService savingsService;

    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findAll(){
        return savingsService.findAll();
    }

    @GetMapping("/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Saving findById(@PathVariable("id") Integer id){
        return savingsService.findById(id);
    }

    @GetMapping("/savings/status")
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByStatus(@RequestParam("status") Status status){
        return savingsService.findByStatus(status);
    }

    @GetMapping("/savings/minimum-balance")
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByMinimumBalance(@RequestParam("minimum_balance") BigDecimal minimumBalance){
        return savingsService.findByMinimumBalance(minimumBalance);
    }

    @GetMapping("/savings/interest-rate")
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByInterestRate(@RequestParam("interest_rate") BigDecimal interestRate){
        return savingsService.findByInterestRate(interestRate);
    }
}
