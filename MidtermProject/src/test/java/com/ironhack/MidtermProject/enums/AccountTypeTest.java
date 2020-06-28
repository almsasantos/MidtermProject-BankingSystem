package com.ironhack.MidtermProject.enums;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTypeTest {
    private AccountType accountType1;
    private AccountType accountType2;
    private AccountType accountType3;
    private AccountType accountType4;

    @BeforeEach
    void setUp() {
        accountType1 = AccountType.CHECKING;
        accountType2 = AccountType.STUDENT_CHECKING;
        accountType3 = AccountType.SAVINGS;
        accountType4 = AccountType.CREDIT_CARD;
    }

    @AfterEach
    void tearDown() {
        accountType1 = null;
        accountType2 = null;
        accountType3 = null;
        accountType4 = null;
    }

    @Test
    void getDescription() {
        assertEquals("The type of Account is Checking", accountType1.getDescription());
        assertEquals("The type of Account is Student Checking", accountType2.getDescription());
        assertEquals("The type of Account is Saving", accountType3.getDescription());
        assertEquals("The type of Account is CreditCard", accountType4.getDescription());
    }
}