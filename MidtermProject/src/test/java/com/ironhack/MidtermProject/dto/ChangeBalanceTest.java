package com.ironhack.MidtermProject.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ChangeBalanceTest {
    private ChangeBalance changeBalance;

    @BeforeEach
    void setUp() {
        changeBalance = new ChangeBalance();
        changeBalance.setAccountId(1);
        changeBalance.setAccountOwnerName("Ana");
        changeBalance.setAmount(new BigDecimal("1000"));
    }

    @AfterEach
    void tearDown() {
        changeBalance = null;
    }

    @Test
    void getAccountId() {
        assertEquals(1, changeBalance.getAccountId());
    }

    @Test
    void getAmount() {
        assertEquals(new BigDecimal("1000"), changeBalance.getAmount());
    }

    @Test
    void getAccountPrimaryOwnerName() {
        assertEquals("Ana", changeBalance.getAccountOwnerName());
    }
}