package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
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

    public void interestRateGain(Integer savingsId) {
        Saving saving = savingsRepository.findById(savingsId).orElseThrow(() -> new DataNotFoundException("Savings Account id not found"));
        if (saving.getLastInterestDate() == null) {
            Integer years = Period.between(LocalDate.now(), saving.getDate()).getYears();
            if (years >= 1) {
                BigDecimal interest = saving.getBalance().multiply(saving.getInterestRate());
                saving.setBalance(saving.getBalance().add(interest));
                saving.setLastInterestDate(LocalDate.now());
                savingsRepository.save(saving);
            }
        } else {
            Integer years = Period.between(LocalDate.now(), saving.getLastInterestDate()).getYears();
            if (years >= 1) {
                BigDecimal interest = saving.getBalance().multiply(saving.getInterestRate());
                saving.setBalance(saving.getBalance().add(interest));
                saving.setLastInterestDate(LocalDate.now());
                savingsRepository.save(saving);
            }
        }
    }
}
