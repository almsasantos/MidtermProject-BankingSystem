package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.service.accounts.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CheckingController {
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAll(){
        return checkingService.findAll();
    }

    @GetMapping("/checking-accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@PathVariable("id") Integer id){
        return checkingService.findById(id);
    }

    @GetMapping("/checking-accounts/status")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByStatus(@RequestParam("status") Status status){
        return checkingService.findByStatus(status);
    }

    @GetMapping("/checking-accounts/minimum-balance")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByMinimumBalance(@RequestParam("minimum_balance") BigDecimal minimumBalance){
        return checkingService.findByMinimumBalance(minimumBalance);
    }

    @GetMapping("/checking-accounts/monthly-maintenance-fee")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByMonthlyMaintenanceFee(@RequestParam("monthly_maintenance_fee") BigDecimal monthlyMaintenanceFee){
        return checkingService.findByMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

}
