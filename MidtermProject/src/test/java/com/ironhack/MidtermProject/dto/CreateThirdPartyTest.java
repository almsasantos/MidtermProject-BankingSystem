package com.ironhack.MidtermProject.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateThirdPartyTest {
    private CreateThirdParty createThirdParty;

    @BeforeEach
    void setUp() {
        createThirdParty = new CreateThirdParty();
    }

    @AfterEach
    void tearDown() {
        createThirdParty = null;
    }

    @Test
    void getName() {
        createThirdParty.setName("Ana");
        assertEquals("Ana", createThirdParty.getName());
    }

    @Test
    void getHashedName() {
        createThirdParty.setHashedName("Toyota");
        assertEquals("Toyota", createThirdParty.getHashedName());
    }

    @Test
    void getPassword() {
        createThirdParty.setPassword("pass");
        assertEquals("pass", createThirdParty.getPassword());
    }
}