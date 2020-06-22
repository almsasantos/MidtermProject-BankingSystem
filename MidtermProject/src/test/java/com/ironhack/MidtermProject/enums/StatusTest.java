package com.ironhack.MidtermProject.enums;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {
    private Status status;
    private Status status1;

    @BeforeEach
    void setUp() {
        status = Status.ACTIVE;
        status1 = Status.FROZEN;
    }

    @AfterEach
    void tearDown() {
        status = null;
        status1 = null;
    }

    @Test
    void getDescription() {
        assertEquals("The Account is active", status.getDescription());
        assertEquals("The Account is frozen and cannot be touched", status1.getDescription());
    }
}