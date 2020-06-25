package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SavingsServiceTest {
    @Autowired
    private SavingsService savingsService;

    @MockBean
    private SavingRepository savingRepository;

    private Saving saving;
    private Saving saving1;
    private List<Saving> savingList = new ArrayList<Saving>();

    @BeforeEach
    void setUp() {
        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        saving.setDate(LocalDate.of(2019, 03, 03));
        saving.setLastInterestDate(null);

        saving1 = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));

        savingList.add(saving);

        when(savingRepository.findAll()).thenReturn(savingList);
        when(savingRepository.findById(saving.getAccountId())).thenReturn(Optional.of(saving));
        when(savingRepository.findByStatus(saving.getStatus())).thenReturn(savingList);
        when(savingRepository.findByMinimumBalance(saving.getMinimumBalance())).thenReturn(savingList);
        when(savingRepository.findByInterestRate(saving.getInterestRate())).thenReturn(savingList);
        when(savingRepository.findByDate(saving.getDate())).thenReturn(savingList);
    }

    @Test
    void findAll() {
        assertEquals(savingList, savingsService.findAll());
    }

    @Test
    void findById() {
        assertEquals(saving, savingsService.findById(saving.getAccountId()));
    }

    @Test
    void findByStatus() {
        assertEquals(savingList, savingsService.findByStatus(saving.getStatus()));
    }

    @Test
    void findByMinimumBalance() {
        assertEquals(savingList, savingsService.findByMinimumBalance(saving.getMinimumBalance()));
    }

    @Test
    void findByInterestRate() {
        assertEquals(savingList, savingsService.findByInterestRate(saving.getInterestRate()));
    }

    @Test
    void findByDate() {
        assertEquals(savingList, savingsService.findByDate(saving.getDate()));
    }

    @Test
    void interestRateGain() {
        savingsService.interestRateGain(saving.getAccountId());
        savingsService.interestRateGain(saving1.getAccountId());

        assertEquals(saving1.getBalance(), saving.getBalance());
    }
}