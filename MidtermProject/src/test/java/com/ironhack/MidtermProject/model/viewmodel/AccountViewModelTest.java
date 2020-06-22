package com.ironhack.MidtermProject.model.viewmodel;

import com.ironhack.MidtermProject.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountViewModelTest {
    private AccountViewModel accountViewModel;

    @BeforeEach
    void setUp() {
        accountViewModel = new AccountViewModel();
        accountViewModel.setBalance(new BigDecimal("10000"));
        accountViewModel.setPrimaryOwnerId(1);
        accountViewModel.setSecondaryOwnerId(2);
        accountViewModel.setSecretKey("999999");
        accountViewModel.setStatus(Status.ACTIVE);
    }

    @AfterEach
    void tearDown() {
        accountViewModel = null;
    }

    @Test
    void getBalance() {
        assertEquals(new BigDecimal("10000"), accountViewModel.getBalance());
    }

    @Test
    void getSecretKey() {
        assertEquals("999999", accountViewModel.getSecretKey());
    }

    @Test
    void getStatus() {
        assertEquals(Status.ACTIVE, accountViewModel.getStatus());
    }

    @Test
    void getPrimaryOwnerId() {
        assertEquals(1, accountViewModel.getPrimaryOwnerId());
    }

    @Test
    void getSecondaryOwnerId() {
        assertEquals(2, accountViewModel.getSecondaryOwnerId());
    }

}