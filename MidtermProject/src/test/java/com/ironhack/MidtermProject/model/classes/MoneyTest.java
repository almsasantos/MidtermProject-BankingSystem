package com.ironhack.MidtermProject.model.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    private Money money;
    private Currency currency;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    @BeforeEach
    void setUp() {
        money = new Money();
        money = new Money(new BigDecimal("100"), currency);
        money = new Money(new BigDecimal("100"), currency, DEFAULT_ROUNDING);

    }

    @AfterEach
    void tearDown() {
        money = null;
    }

    @Test
    void increaseAmount() {
        BigDecimal balance = new BigDecimal("100");
        money = new Money(balance);
        assertEquals(new Money(new BigDecimal("10")), money.increaseAmount(new Money(new BigDecimal("10"))));

    }

    @Test
    void testIncreaseAmount() {
    }

    @Test
    void decreaseAmount() {
    }

    @Test
    void testDecreaseAmount() {
    }

    @Test
    void getCurrency() {
    }

    @Test
    void getAmount() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}