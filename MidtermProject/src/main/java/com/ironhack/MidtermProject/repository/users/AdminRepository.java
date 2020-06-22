package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public List<Admin> findByName(String name);

    @Query(value = "SELECT balance FROM savings WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkSavingsBalance(@Param("account_id") Integer accountId);

    @Query(value = "SELECT balance FROM checking WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkCheckingBalance(@Param("account_id") Integer accountId);

    @Query(value = "SELECT balance FROM student_checking WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkStudentCheckingBalance(@Param("account_id") Integer accountId);

    @Query(value = "SELECT balance FROM credit_card WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkCreditCardBalance(@Param("account_id") Integer accountId);
}
