package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
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

    public List<Saving> findAll(){
        return savingsRepository.findAll();
    }

    public Saving findById(Integer id){
        Saving saving = savingsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Savings Account id not found"));
        interestRateGain(saving.getAccountId());
        return saving;
    }

    public List<Saving> findBySecretKey(String secretKey){
        return savingsRepository.findBySecretKey(secretKey);
    }

    public List<Saving> findByStatus(Status status){
        return savingsRepository.findByStatus(status);
    }

    public List<Saving> findByMinimumBalance(BigDecimal minimumBalance){
        return savingsRepository.findByMinimumBalance(minimumBalance);
    }

    public List<Saving> findByInterestRate(BigDecimal interestRate){
        return savingsRepository.findByInterestRate(interestRate);
    }

    public List<Saving> findByDate(LocalDate date){
        return savingsRepository.findByDate(date);
    }

    public void interestRateGain(Integer savingsId) {
        Saving saving = savingsRepository.findById(savingsId).orElseThrow(() -> new DataNotFoundException("Savings Account id not found"));
        if (saving.getLastInterestDate() == null) {
            Integer years = Period.between(LocalDate.now(), saving.getDate()).getYears();
            if (years >= 1) {
                BigDecimal interest = saving.getBalance().getAmount().multiply(saving.getInterestRate());
                saving.setBalance(new Money(saving.getBalance().increaseAmount(interest)));
                saving.setLastInterestDate(LocalDate.now());
                savingsRepository.save(saving);
            }
        } else {
            Integer years = Period.between(LocalDate.now(), saving.getLastInterestDate()).getYears();
            if (years >= 1) {
                BigDecimal interest = saving.getBalance().getAmount().multiply(saving.getInterestRate());
                saving.setBalance(new Money(saving.getBalance().increaseAmount(interest)));
                saving.setLastInterestDate(LocalDate.now());
                savingsRepository.save(saving);
            }
        }
    }
}
