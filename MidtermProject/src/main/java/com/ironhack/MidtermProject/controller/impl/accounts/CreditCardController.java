package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.CreditCard;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/credit_cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAll(){
        return creditCardService.findAll();
    }

    @GetMapping("/credit_cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard findById(@PathVariable("id") Integer id){
        return creditCardService.findById(id);
    }

}
