package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.controller.interfaces.accounts.SavingsControllerInterface;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Savings account Controller")
@RestController
@RequestMapping("/")
public class SavingsController implements SavingsControllerInterface {
    @Autowired
    private SavingsService savingsService;

    /**
     * Find all Saving accounts.
     * @return a list of saving accounts.
     */
    @GetMapping("/savings")
    @ApiOperation(value="Find All Savings Accounts",
            notes = "Lists all savings accounts created",
            response = Saving.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findAll(){
        return savingsService.findAll();
    }

    /**
     * Find Saving accounts by Id.
     * @param id receives an integer id of account.
     * @return a saving account corresponding to that id.
     */
    @GetMapping("/savings/{id}")
    @ApiOperation(value="Find Savings Account by Id",
            response = Saving.class)
    @ResponseStatus(HttpStatus.OK)
    public Saving findById(@PathVariable("id") Integer id){
        return savingsService.findById(id);
    }

    /**
     * Find Saving accounts based on the status.
     * @param status receives an enum of Status.
     * @return a list of saving accounts.
     */
    @GetMapping("/savings/status")
    @ApiOperation(value="Find Savings Accounts by Status",
            notes = "Lists all savings accounts by status",
            response = Saving.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByStatus(@RequestParam("status") Status status){
        return savingsService.findByStatus(status);
    }

    /**
     * Find Saving accounts based on the minimumBalance.
     * @param minimumBalance receives a BigDecimal which corresponds to the minimumBalance.
     * @return a list of saving accounts.
     */
    @GetMapping("/savings/minimum-balance")
    @ApiOperation(value="Find Savings Accounts by Minimum Balance",
            notes = "Lists all savings accounts by minimum balance",
            response = Saving.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByMinimumBalance(@RequestParam("minimum_balance") BigDecimal minimumBalance){
        return savingsService.findByMinimumBalance(minimumBalance);
    }

    /**
     * Find Saving accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of saving accounts.
     */
    @GetMapping("/savings/interest-rate")
    @ApiOperation(value="Find Savings Accounts by Interest Rate",
            notes = "Lists all savings accounts by interest rate",
            response = Saving.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Saving> findByInterestRate(@RequestParam("interest_rate") BigDecimal interestRate){
        return savingsService.findByInterestRate(interestRate);
    }
}
