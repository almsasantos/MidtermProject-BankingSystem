package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SavingsServiceTest {

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private SavingsService savingsService;

    private Saving saving;

    private List<Saving> savingList = new ArrayList<Saving>();

    @BeforeEach
    void setUp() {
        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.0250"), new BigDecimal("1000.0000"));
        saving.setLastInterestDate(null);
        savingRepository.save(saving);

        savingList.add(saving);
    }

    @AfterEach
    void tearDown(){
        savingRepository.deleteAll();
    }

    @Test
    void findAll() {
        assertEquals(1, savingsService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(saving.getAccountId(), savingsService.findById(saving.getAccountId()).getAccountId());
    }

    @Test
    void findByStatus() {
        assertEquals(1, savingsService.findByStatus(saving.getStatus()).size());
    }

    @Test
    void findByMinimumBalance() {
        assertEquals(1, savingsService.findByMinimumBalance(saving.getMinimumBalance()).size());
    }

    @Test
    void findByInterestRate() {
        assertEquals(1, savingsService.findByInterestRate(saving.getInterestRate()).size());
    }

    @Test
    void findByDate() {
        assertEquals(1, savingsService.findByDate(saving.getDate()).size());
    }

    @Test
    void interestRateGain() {
        assertThrows(DataNotFoundException.class, () -> {
            savingsService.interestRateGain(55);});;
    }
}