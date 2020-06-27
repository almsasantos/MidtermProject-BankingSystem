package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class SavingsService {
    @Autowired
    private SavingRepository savingsRepository;

    private static final Logger LOGGER = LogManager.getLogger(SavingsService.class);

    /**
     * Find all Saving accounts.
     * @return a list of saving accounts.
     */
    public List<Saving> findAll(){
        LOGGER.info("Get all Savings accounts");
        return savingsRepository.findAll();
    }

    /**
     * Find Saving accounts by Id.
     * @param id receives an integer id of account.
     * @return a saving account corresponding to that id.
     */
    public Saving findById(Integer id){
        LOGGER.info("Get Savings account with id " + id);
        Saving saving = savingsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Savings Account id not found"));
        return saving;
    }

    /**
     * Find Saving accounts based on the status.
     * @param status receives an enum of Status.
     * @return a list of saving accounts.
     */
    public List<Saving> findByStatus(Status status){
        LOGGER.info("Get Savings account with status " + status);
        return savingsRepository.findByStatus(status);
    }

    /**
     * Find Saving accounts based on the minimumBalance.
     * @param minimumBalance receives a BigDecimal which corresponds to the minimumBalance.
     * @return a list of saving accounts.
     */
    public List<Saving> findByMinimumBalance(BigDecimal minimumBalance){
        LOGGER.info("Get Savings account with minimum balance of " + minimumBalance);
        return savingsRepository.findByMinimumBalance(minimumBalance);
    }

    /**
     * Find Saving accounts based on the interestRate.
     * @param interestRate receives a BigDecimal which corresponds to the interestRate.
     * @return a list of saving accounts.
     */
    public List<Saving> findByInterestRate(BigDecimal interestRate){
        LOGGER.info("Get Savings account with interest rate of " + interestRate);
        return savingsRepository.findByInterestRate(interestRate);
    }

    /**
     * Find all Saving accounts based on the date of creation.
     * @param date receives a LocalDate which corresponds to the date.
     * @return a list of saving accounts.
     */
    public List<Saving> findByDate(LocalDate date){
        LOGGER.info("Get Savings account with created in " + date);
        return savingsRepository.findByDate(date);
    }

    /**
     * Applies interest rate gain if a year has passed since the creation of account or since the last update of the interest.
     * @param savingsId receives an integer id of account.
     */
    public void interestRateGain(Integer savingsId) {
        LOGGER.info("[INIT] Interest Rate Gain in account " + savingsId);

        Saving saving = savingsRepository.findById(savingsId).orElseThrow(() -> new DataNotFoundException("Savings Account id not found"));
        Integer years = Period.between(LocalDate.now(), saving.getLastInterestDate()).getYears();
            if (years >= 1) {
                BigDecimal interest = saving.getBalance().getAmount().multiply(saving.getInterestRate()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                LOGGER.info("Increment balance from " + savingsId + " account with " + interest + " since a year passed from last gained");
                saving.setBalance(new Money(saving.getBalance().increaseAmount(interest)));
                saving.setLastInterestDate(LocalDate.now());
                savingsRepository.save(saving);
                LOGGER.info("Interest rate was added to " + savingsId);
            }

        LOGGER.info("[END] Interest Rate Gain in account " + savingsId);
    }
}
