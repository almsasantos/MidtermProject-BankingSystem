package com.ironhack.MidtermProject.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferenceTest {
    private Transference transference;
    private Transference transference1;

    @BeforeEach
    void setUp() {
        transference = new Transference();
        transference.setUserId(1);
        transference.setSenderName("Ana");
        transference.setSenderAccountId(4);
        transference.setReceiverName("Rachel");
        transference.setReceiverAccountId(5);
        transference.setAmountToTransfer(new BigDecimal("100"));
        transference1 = new Transference(1, "Ana", 4, "Rachel", 5, new BigDecimal("100"));
    }

    @AfterEach
    void tearDown() {
        transference = null;
        transference1 = null;
    }

    @Test
    void getUserId() {
        assertEquals(1, transference.getUserId());
    }

    @Test
    void getSenderAccountId() {
        assertEquals(4, transference.getSenderAccountId());
    }

    @Test
    void getReceiverAccountId() {
        assertEquals(5, transference.getReceiverAccountId());
    }

    @Test
    void getAmountToTransfer() {
        assertEquals(new BigDecimal("100"), transference.getAmountToTransfer());
    }

    @Test
    void getSenderName() {
        assertEquals("Ana", transference.getSenderName());
    }

    @Test
    void getReceiverName() {
        assertEquals("Rachel", transference.getReceiverName());
    }

    @Test
    void testToString() {
        String t = transference.toString();
        assertEquals(t, transference.toString());
    }

    @Test
    void testEquals() {
        assertEquals(transference, transference1);
    }

    @Test
    void testHashCode() {
        assertEquals(transference.hashCode(), transference1.hashCode());
    }
}