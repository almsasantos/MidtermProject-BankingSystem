package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    /**
     * Find Savings Account based on their status.
     * @param status receives a enum status.
     * @return a list of savings accounts.
     */
    public List<Saving> findByStatus(Status status);

    /**
     * Find Savings Account based on their minimumBalance.
     * @param minimumBalance receives a BigDecimal with minimumBalance.
     * @return a list of savings accounts.
     */
    public List<Saving> findByMinimumBalance(BigDecimal minimumBalance);

    /**
     * Find Savings Account based on their interestRate.
     * @param interestRate receives a BigDecimal with interestRate.
     * @return a list of savings accounts.
     */
    public List<Saving> findByInterestRate(BigDecimal interestRate);

    /**
     * Find Savings Account based on their creation date.
     * @param date receives a LocalDate with date.
     * @return a list of savings accounts.
     */
    public List<Saving> findByDate(LocalDate date);
}
