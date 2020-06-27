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
    /**
     * Find Admin users based on the name.
     * @param name receives a string name.
     * @return a list of Admin that share that name.
     */
    public List<Admin> findByName(String name);

    /**
     * Find balance from Savings Account provided.
     * @param accountId receives an integer with id from savings account.
     * @return a BigDecimal of Balance of account.
     */
    @Query(value = "SELECT balance FROM savings WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkSavingsBalance(@Param("account_id") Integer accountId);

    /**
     * Find balance from Checking Account provided.
     * @param accountId receives an integer with id from checking account.
     * @return a BigDecimal of Balance of account.
     */
    @Query(value = "SELECT balance FROM checking WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkCheckingBalance(@Param("account_id") Integer accountId);

    /**
     * Find balance from StudentChecking Account provided.
     * @param accountId receives an integer with id from student checking account.
     * @return a BigDecimal of Balance of account.
     */
    @Query(value = "SELECT balance FROM student_checking WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkStudentCheckingBalance(@Param("account_id") Integer accountId);

    /**
     * Find balance from CreditCard Account provided.
     * @param accountId receives an integer with id from credit card account.
     * @return a BigDecimal of Balance of account.
     */
    @Query(value = "SELECT balance FROM credit_card WHERE account_id = :account_id", nativeQuery = true)
    public BigDecimal checkCreditCardBalance(@Param("account_id") Integer accountId);
}
