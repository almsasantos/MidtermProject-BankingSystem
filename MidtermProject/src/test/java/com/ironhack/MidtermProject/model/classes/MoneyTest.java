package com.ironhack.MidtermProject.model.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

class MoneyTest {
    private Money money;
    private Currency currency;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    @BeforeEach
    void setUp() {
        money = new Money();
        money = new Money(new BigDecimal("100"), currency, DEFAULT_ROUNDING);

    }

    @AfterEach
    void tearDown() {
        money = null;
    }
}