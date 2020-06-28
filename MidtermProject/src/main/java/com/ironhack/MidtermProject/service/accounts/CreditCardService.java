package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    private static final Logger LOGGER = LogManager.getLogger(CreditCardService.class);

    /**
     * Find all CreditCard accounts.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findAll(){
        LOGGER.info("Get all Credit Card accounts");
        return creditCardRepository.findAll();
    }

    /**
     * Find CreditCard accounts by Id.
     * @param id receives an integer id of account.
     * @return a credit credit account corresponding to that id.
     */
    public CreditCard findById(Integer id){
        LOGGER.info("Get Credit Card account with id " + id);
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Credit Card id not found"));
        interestDateGainMonthly(creditCard.getAccountId());
        return creditCard;
    }

    /**
     * Applies interest rate gain if a month has passed since the creation of account or since the last update of the interest.
     * @param accountId receives an integer id of account.
     */
    public void interestDateGainMonthly(Integer accountId) {
        LOGGER.info("[INIT] Interest Rate Gain in account " + accountId);

        LOGGER.info("Confirm if account with id " + accountId + " exists");
        CreditCard creditCard = creditCardRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Credit Card id not found"));
        Integer months = Period.between(LocalDate.now(), creditCard.getLastInterestDate()).getMonths();
        if (months >= 1) {
                BigDecimal interest = creditCard.getInterestRate().divide(new BigDecimal("12"), 5, RoundingMode.HALF_DOWN).multiply(creditCard.getBalance().getAmount());
                creditCard.setBalance(new Money(creditCard.getBalance().increaseAmount(interest)));
                creditCard.setLastInterestDate(LocalDate.now());
                creditCardRepository.save(creditCard);
                LOGGER.info("Interest rate was added to " + accountId);
        }

        LOGGER.info("[END] Interest Rate Gain in account " + accountId);
    }

    /**
     * Find all CreditCard accounts based on the creditLimit.
     * @param creditLimit receives a BigDecimal which corresponds to the creditLimit.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findByCreditLimit(BigDecimal creditLimit){
        LOGGER.info("Get Credit Card account with credit limit of " + creditLimit);
        return creditCardRepository.findByCreditLimit(creditLimit);
    }

    /**
     * Find all CreditCard accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findByInterestRate(BigDecimal interestRate){
        LOGGER.info("Get Credit Card account with interest rate of " + interestRate);
        return creditCardRepository.findByInterestRate(interestRate);
    }

    /**
     * Find all CreditCard accounts based on the date of creation.
     * @param date receives a LocalDate which corresponds to the date.
     * @return a list of credit credit accounts.
     */
    public List<CreditCard> findByDate(LocalDate date){
        LOGGER.info("Get Credit Card account with created in " + date);
        return creditCardRepository.findByDate(date);
    }
}
