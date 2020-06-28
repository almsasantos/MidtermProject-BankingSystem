package com.ironhack.MidtermProject.model.entities.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ThirdPartyTest {
    private ThirdParty thirdParty;
    private ThirdParty thirdParty1;

    @BeforeEach
    void setUp() {
        thirdParty = new ThirdParty();
        thirdParty1 = new ThirdParty("Ana", "pass");
    }

    @AfterEach
    void tearDown() {
        thirdParty = null;
        thirdParty1 = null;
    }

    @Test
    void getAccountDetails() {
        thirdParty.setAccountDetails(new HashMap<String, String>());
        assertEquals(0, thirdParty.getAccountDetails().size());
    }

    @Test
    void testToString() {
        String thirdPartyString = thirdParty1.toString();
        assertEquals(thirdPartyString, thirdParty1.toString());
    }

    @Test
    void testEquals() {
        thirdParty.setName("Ana");
        thirdParty.setPassword("pass");
        assertEquals(thirdParty, thirdParty1);
    }

    @Test
    void testHashCode() {
        assertEquals(thirdParty.hashCode(), thirdParty.hashCode());
    }
}