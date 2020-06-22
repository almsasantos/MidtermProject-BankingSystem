package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
