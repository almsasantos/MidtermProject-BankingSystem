package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/credit-cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAll(){
        return creditCardService.findAll();
    }

    @GetMapping("/credit-cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard findById(@PathVariable("id") Integer id){
        return creditCardService.findById(id);
    }

    @GetMapping("/credit-cards/credit-limit")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findByCreditLimit(@RequestParam("credit_limit") BigDecimal creditLimit){
        return creditCardService.findByCreditLimit(creditLimit);
    }

    @GetMapping("/credit-cards/interest-rate")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findByInterestRate(@RequestParam("interest_rate") BigDecimal interestRate){
        return creditCardService.findByInterestRate(interestRate);
    }
}
