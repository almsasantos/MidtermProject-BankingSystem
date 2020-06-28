package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {
    /**
     * Find Checking Account based on their status.
     * @param status receives a enum status.
     * @return a list of checking accounts.
     */
    public List<Checking> findByStatus(Status status);

    /**
     * Find Checking Account based on their minimumBalance.
     * @param minimumBalance receives a BigDecimal with minimumBalance.
     * @return a list of checking accounts.
     */
    public List<Checking> findByMinimumBalance(BigDecimal minimumBalance);

    /**
     * Find Checking Account based on their monthlyMaintenanceFee.
     * @param monthlyMaintenanceFee receives a BigDecimal with monthlyMaintenanceFee.
     * @return a list of checking accounts.
     */
    public List<Checking> findByMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee);
}
