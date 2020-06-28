package com.ironhack.MidtermProject.controller.interfaces.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;

import java.math.BigDecimal;
import java.util.List;

public interface CreditCardControllerInterface {

    /**
     * Find all CreditCard accounts.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findAll();

    /**
     * Find CreditCard accounts by Id.
     * @param id receives an integer id of account.
     * @return a credit credit account corresponding to that id.
     */
    public CreditCard findById(Integer id);

    /**
     * Find all CreditCard accounts based on the creditLimit.
     * @param creditLimit receives a BigDecimal which corresponds to the creditLimit.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findByCreditLimit(BigDecimal creditLimit);

    /**
     * Find all CreditCard accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findByInterestRate(BigDecimal interestRate);
}
