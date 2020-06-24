package com.ironhack.MidtermProject.model.entities.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminTest {
    private Admin admin;
    private Admin admin1;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setUserId(1);
        admin.isLogged();
    }

    @AfterEach
    void tearDown() {
        admin = null;
    }

    @Test
    void testAdminToString(){
        admin = new Admin("Ana", "pass");
        String adminString = admin.toString();
        assertEquals(adminString, admin.toString());
    }
}