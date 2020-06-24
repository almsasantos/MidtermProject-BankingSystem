package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    public List<CreditCard> findByCreditLimit(BigDecimal creditLimit);

    public List<CreditCard> findByInterestRate(BigDecimal interestRate);

    public List<CreditCard> findByDate(LocalDate date);
}
