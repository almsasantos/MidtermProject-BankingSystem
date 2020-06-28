package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    /**
     * Find CreditCard Account based on their creditLimit.
     * @param creditLimit receives a BigDecimal with creditLimit.
     * @return a list of credit card accounts.
     */
    public List<CreditCard> findByCreditLimit(BigDecimal creditLimit);

    /**
     * Find CreditCard Account based on their interestRate.
     * @param interestRate receives a BigDecimal with interestRate.
     * @return a list of credit card accounts.
     */
    public List<CreditCard> findByInterestRate(BigDecimal interestRate);

    /**
     * Find CreditCard Account based on their creation date.
     * @param date receives a LocalDate with date.
     * @return a list of credit card accounts.
     */
    public List<CreditCard> findByDate(LocalDate date);
}
