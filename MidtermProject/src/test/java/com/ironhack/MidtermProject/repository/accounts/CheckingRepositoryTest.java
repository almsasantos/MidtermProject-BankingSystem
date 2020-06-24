package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CheckingRepositoryTest {
    @Autowired
    private CheckingRepository checkingRepository;

    private Checking checking;

    @BeforeEach
    void setUp() {
        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checking.setLastPenalty(0);
        checkingRepository.save(checking);
    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
    }

    @Test
    void findBySecretKey() {
        assertEquals(1, checkingRepository.findBySecretKey("000000").size());
    }

    @Test
    void findByStatus() {
        assertEquals(1, checkingRepository.findByStatus(Status.ACTIVE).size());
    }

    @Test
    void findByMinimumBalance() {
        assertEquals(1, checkingRepository.findByMinimumBalance(new BigDecimal("250")).size());
    }

    @Test
    void findByMonthlyMaintenanceFee() {
        assertEquals(1, checkingRepository.findByMonthlyMaintenanceFee(new BigDecimal("12")).size());
    }
}