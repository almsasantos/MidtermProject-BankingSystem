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
    public List<Saving> findBySecretKey(String secretKey);

    public List<Saving> findByStatus(Status status);

    public List<Saving> findByMinimumBalance(BigDecimal minimumBalance);

    public List<Saving> findByInterestRate(BigDecimal interestRate);

    public List<Saving> findByDate(LocalDate date);
}
