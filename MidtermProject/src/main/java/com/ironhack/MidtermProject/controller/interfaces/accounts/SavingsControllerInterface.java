package com.ironhack.MidtermProject.controller.interfaces.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;

import java.math.BigDecimal;
import java.util.List;

public interface SavingsControllerInterface {

    /**
     * Find all Saving accounts.
     * @return a list of saving accounts.
     */
    public List<Saving> findAll();

    /**
     * Find Saving accounts by Id.
     * @param id receives an integer id of account.
     * @return a saving account corresponding to that id.
     */
    public Saving findById(Integer id);

    /**
     * Find Saving accounts based on the status.
     * @param status receives an enum of Status.
     * @return a list of saving accounts.
     */
    public List<Saving> findByStatus(Status status);

    /**
     * Find Saving accounts based on the minimumBalance.
     * @param minimumBalance receives a BigDecimal which corresponds to the minimumBalance.
     * @return a list of saving accounts.
     */
    public List<Saving> findByMinimumBalance(BigDecimal minimumBalance);

    /**
     * Find Saving accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of saving accounts.
     */
    public List<Saving> findByInterestRate(BigDecimal interestRate);

}
