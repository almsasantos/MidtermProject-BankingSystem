package com.ironhack.MidtermProject.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginAccountTest {
    private LoginAccount loginAccount;

    @BeforeEach
    void setUp() {
        loginAccount = new LoginAccount();
    }

    @AfterEach
    void tearDown() {
        loginAccount = null;
    }

    @Test
    void getId() {
        loginAccount.setId(1);
        assertEquals(1, loginAccount.getId());
    }

    @Test
    void getPassword() {
        loginAccount.setPassword("pass");
        assertEquals("pass", loginAccount.getPassword());
    }
}