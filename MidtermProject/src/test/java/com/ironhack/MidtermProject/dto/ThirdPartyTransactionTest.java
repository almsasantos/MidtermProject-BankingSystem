package com.ironhack.MidtermProject.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThirdPartyTransactionTest {
    private ThirdPartyTransaction thirdPartyTransaction;

    @BeforeEach
    void setUp() {
        thirdPartyTransaction = new ThirdPartyTransaction();
        thirdPartyTransaction.setAccountId(1);
        thirdPartyTransaction.setSecretKey("000000");
        thirdPartyTransaction.setAmount(new BigDecimal("1000"));
    }

    @AfterEach
    void tearDown() {
        thirdPartyTransaction = null;
    }

    @Test
    void getAccountId() {
        assertEquals(1, thirdPartyTransaction.getAccountId());
    }

    @Test
    void getSecretKey() {
        assertEquals("000000", thirdPartyTransaction.getSecretKey());
    }

    @Test
    void getAmount() {
        assertEquals(new BigDecimal("1000"), thirdPartyTransaction.getAmount());
    }
}