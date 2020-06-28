package com.ironhack.MidtermProject.model.viewmodel;

import com.ironhack.MidtermProject.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SavingViewModelTest {
    private SavingViewModel savingViewModel;

    @BeforeEach
    void setUp() {
        savingViewModel = new SavingViewModel();
        savingViewModel.setBalance(new BigDecimal("10000"));
        savingViewModel.setPrimaryOwnerId(1);
        savingViewModel.setSecondaryOwnerId(2);
        savingViewModel.setSecretKey("999999");
        savingViewModel.setStatus(Status.ACTIVE);
        savingViewModel.setMinimumBalance(new BigDecimal("100"));
        savingViewModel.setInterestRate(new BigDecimal("0.2"));
    }

    @AfterEach
    void tearDown() {
        savingViewModel = null;
    }

    @Test
    void getBalance() {
        assertEquals(new BigDecimal("10000"), savingViewModel.getBalance());
    }

    @Test
    void getSecretKey() {
        assertEquals("999999", savingViewModel.getSecretKey());
    }


    @Test
    void getStatus() {
        assertEquals(Status.ACTIVE, savingViewModel.getStatus());
    }

    @Test
    void getPrimaryOwnerId() {
        assertEquals(1, savingViewModel.getPrimaryOwnerId());
    }

    @Test
    void getSecondaryOwnerId() {
        assertEquals(2, savingViewModel.getSecondaryOwnerId());
    }

    @Test
    void getInterestRate() {
        assertEquals(new BigDecimal("0.2"), savingViewModel.getInterestRate());
    }

    @Test
    void getMinimumBalance() {
        assertEquals(new BigDecimal("100"), savingViewModel.getMinimumBalance());
    }

}