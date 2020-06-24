package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;

    public List<CreditCard> findAll(){
        return creditCardRepository.findAll();
    }

    public CreditCard findById(Integer id){
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Credit Card id not found"));
        interestDateGainMonthly(creditCard.getAccountId());
        return creditCard;
    }

    public void interestDateGainMonthly(Integer accountId) {
        CreditCard creditCard = creditCardRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Credit Card id not found"));
        if (creditCard.getLastInterestDate() == null) {
            Integer months = Period.between(LocalDate.now(), creditCard.getDate()).getMonths();
            if (months >= 1) {
                BigDecimal interest = creditCard.getInterestRate().divide(new BigDecimal("12")).multiply(creditCard.getBalance().getAmount());
                creditCard.setBalance(new Money(creditCard.getBalance().increaseAmount(interest)));
                creditCard.setLastInterestDate(LocalDate.now());
                creditCardRepository.save(creditCard);
            }
        } else {
            Integer months = Period.between(LocalDate.now(), creditCard.getLastInterestDate()).getMonths();
            if (months >= 1) {
                BigDecimal interest = creditCard.getInterestRate().divide(new BigDecimal("12")).multiply(creditCard.getBalance().getAmount());
                creditCard.setBalance(new Money(creditCard.getBalance().increaseAmount(interest)));
                creditCard.setLastInterestDate(LocalDate.now());
                creditCardRepository.save(creditCard);
            }
        }
    }

    public List<CreditCard> findByCreditLimit(BigDecimal creditLimit){
        return creditCardRepository.findByCreditLimit(creditLimit);
    }

    public List<CreditCard> findByInterestRate(BigDecimal interestRate){
        return creditCardRepository.findByInterestRate(interestRate);
    }

    public List<CreditCard> findByDate(LocalDate date){
        return creditCardRepository.findByDate(date);
    }
}
