package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SavingRepositoryTest {
    @Autowired
    private SavingRepository savingRepository;

    private Saving saving;

    @BeforeEach
    void setUp() {
        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving);
    }

    @AfterEach
    void tearDown() {
        savingRepository.deleteAll();
    }

    @Test
    void findByStatus() {
        assertEquals(1, savingRepository.findByStatus(Status.ACTIVE).size());
    }

    @Test
    void findByMinimumBalance() {
        System.out.println(saving.getMinimumBalance());
        assertEquals(1, savingRepository.findByMinimumBalance(new BigDecimal("1000")).size());
    }

    @Test
    void findByInterestRate() {
        assertEquals(1, savingRepository.findByInterestRate(new BigDecimal("0.025")).size());
    }

    @Test
    void findByDate() {
        assertEquals(1, savingRepository.findByDate(saving.getDate()).size());
    }
}