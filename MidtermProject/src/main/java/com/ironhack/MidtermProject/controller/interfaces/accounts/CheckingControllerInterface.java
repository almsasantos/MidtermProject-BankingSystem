package com.ironhack.MidtermProject.controller.interfaces.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;

import java.math.BigDecimal;
import java.util.List;

public interface CheckingControllerInterface {

    /**
     * Find all checking accounts created.
     * @return a list of checking accounts.
     */
    public List<Checking> findAll();

    /**
     * Find checking account by id.
     * @param id receives an integer id.
     * @return a Checking which belongs to that id.
     */
    public Checking findById(Integer id);

    /**
     * Find all checking accounts by status.
     * @param status receives a enum status.
     * @return a list of checking accounts.
     */
    public List<Checking> findByStatus(Status status);

    /**
     * Find all checking accounts by minimumBalance.
     * @param minimumBalance receives a BigDecimal with minimumBalance.
     * @return a list of checking accounts.
     */
    public List<Checking> findByMinimumBalance(BigDecimal minimumBalance);

    /**
     * Find all checking accounts by monthlyMaintenanceFee.
     * @param monthlyMaintenanceFee receives a BigDecimal with monthlyMaintenanceFee.
     * @return a list of checking accounts.
     */
    public List<Checking> findByMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee);
}
