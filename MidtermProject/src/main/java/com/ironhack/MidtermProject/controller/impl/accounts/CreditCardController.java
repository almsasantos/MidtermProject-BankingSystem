package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.controller.interfaces.accounts.CreditCardControllerInterface;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Credit Card Account Controller")
@RestController
@RequestMapping("/")
public class CreditCardController implements CreditCardControllerInterface {
    @Autowired
    private CreditCardService creditCardService;

    /**
     * Find all CreditCard accounts.
     * @return a list of credit credit accounts.
     */
    @GetMapping("/credit-cards")
    @ApiOperation(value="Find All Credit Card Accounts",
            notes = "Lists all credit card accounts created",
            response = CreditCard.class)
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAll(){
        return creditCardService.findAll();
    }

    /**
     * Find CreditCard accounts by Id.
     * @param id receives an integer id of account.
     * @return a credit credit account corresponding to that id.
     */
    @GetMapping("/credit-cards/{id}")
    @ApiOperation(value="Find Credit Card Account by id",
            response = CreditCard.class)
    @ResponseStatus(HttpStatus.OK)
    public CreditCard findById(@PathVariable("id") Integer id){
        return creditCardService.findById(id);
    }

    /**
     * Find all CreditCard accounts based on the creditLimit.
     * @param creditLimit receives a BigDecimal which corresponds to the creditLimit.
     * @return a list of credit credit accounts.
     */
    @GetMapping("/credit-cards/credit-limit")
    @ApiOperation(value="Find Credit Card Account by credit limit",
            notes = "Lists all credit card accounts by credit limit",
            response = CreditCard.class)
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findByCreditLimit(@RequestParam("credit_limit") BigDecimal creditLimit){
        return creditCardService.findByCreditLimit(creditLimit);
    }

    /**
     * Find all CreditCard accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of credit credit accounts.
     */
    @GetMapping("/credit-cards/interest-rate")
    @ApiOperation(value="Find Credit Card Accounts by Interest Rate",
            notes = "Lists all credit card accounts by interest rate",
            response = CreditCard.class)
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findByInterestRate(@RequestParam("interest_rate") BigDecimal interestRate){
        return creditCardService.findByInterestRate(interestRate);
    }
}
