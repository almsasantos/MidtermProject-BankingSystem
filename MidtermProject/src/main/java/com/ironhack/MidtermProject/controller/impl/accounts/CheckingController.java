package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.controller.interfaces.accounts.CheckingControllerInterface;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.service.accounts.CheckingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Checking Account Controller")
@RestController
@RequestMapping("/")
public class CheckingController implements CheckingControllerInterface {
    @Autowired
    private CheckingService checkingService;

    /**
     * Find all checking accounts created.
     * @return a list of checking accounts.
     */
    @GetMapping("/checkings")
    @ApiOperation(value="Find All Checking Accounts",
            notes = "Lists all checking accounts created",
            response = Checking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAll(){
        return checkingService.findAll();
    }

    /**
     * Find checking account by id.
     * @param id receives an integer id.
     * @return a Checking which belongs to that id.
     */
    @GetMapping("/checkings/{id}")
    @ApiOperation(value="Find Checking Account by id",
            response = Checking.class)
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@PathVariable("id") Integer id){
        return checkingService.findById(id);
    }

    /**
     * Find all checking accounts by status.
     * @param status receives a enum status.
     * @return a list of checking accounts.
     */
    @GetMapping("/checkings/status")
    @ApiOperation(value="Find All Checking Accounts by status",
            notes = "Lists all checking accounts by status",
            response = Checking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByStatus(@RequestParam("status") Status status){
        return checkingService.findByStatus(status);
    }

    /**
     * Find all checking accounts by minimumBalance.
     * @param minimumBalance receives a BigDecimal with minimumBalance.
     * @return a list of checking accounts.
     */
    @GetMapping("/checkings/minimum-balance")
    @ApiOperation(value="Find All Checking Accounts by Minimum Balance",
            notes = "Lists all checking accounts by minimum balance",
            response = Checking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByMinimumBalance(@RequestParam("minimum_balance") BigDecimal minimumBalance){
        return checkingService.findByMinimumBalance(minimumBalance);
    }

    /**
     * Find all checking accounts by monthlyMaintenanceFee.
     * @param monthlyMaintenanceFee receives a BigDecimal with monthlyMaintenanceFee.
     * @return a list of checking accounts.
     */
    @GetMapping("/checkings/monthly-maintenance-fee")
    @ApiOperation(value="Find All Checking Accounts by Monthly Maintenance Fee",
            notes = "Lists all checking accounts by monthly maintenance fee",
            response = Checking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findByMonthlyMaintenanceFee(@RequestParam("monthly_maintenance_fee") BigDecimal monthlyMaintenanceFee){
        return checkingService.findByMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

}
