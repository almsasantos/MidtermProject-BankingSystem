package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {
    public List<Checking> findBySecretKey(String secretKey);

    public List<Checking> findByStatus(Status status);

    public List<Checking> findByMinimumBalance(BigDecimal minimumBalance);

    public List<Checking> findByMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee);
}
