package com.ironhack.MidtermProject.model.viewmodel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditCardViewModelTest {
    private CreditCardViewModel creditCardViewModel;

    @BeforeEach
    void setUp() {
        creditCardViewModel = new CreditCardViewModel();
        creditCardViewModel.setBalance(new BigDecimal("10000"));
        creditCardViewModel.setPrimaryOwnerId(1);
        creditCardViewModel.setSecondaryOwnerId(2);
        creditCardViewModel.setCreditLimit(new BigDecimal("100"));
        creditCardViewModel.setInterestRate(new BigDecimal("0.2"));
    }

    @AfterEach
    void tearDown() {
        creditCardViewModel = null;
    }

    @Test
    void getBalance() {
        assertEquals(new BigDecimal("10000"), creditCardViewModel.getBalance());
    }

    @Test
    void getPrimaryOwnerId() {
        assertEquals(1, creditCardViewModel.getPrimaryOwnerId());
    }

    @Test
    void getSecondaryOwnerId() {
        assertEquals(2, creditCardViewModel.getSecondaryOwnerId());
    }

    @Test
    void getCreditLimit() {
        assertEquals(new BigDecimal("100"), creditCardViewModel.getCreditLimit());
    }

    @Test
    void getInterestRate() {
        assertEquals(new BigDecimal("0.2"), creditCardViewModel.getInterestRate());
    }
}