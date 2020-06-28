package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentCheckingTest {
    private StudentChecking studentChecking;

    @BeforeEach
    void setUp() {
        studentChecking = new StudentChecking();
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
    }

    @AfterEach
    void tearDown() {
        studentChecking = null;
    }

    @Test
    void getSecretKey() {
        studentChecking.setSecretKey("000000");

        assertEquals("000000", studentChecking.getSecretKey());
    }

    @Test
    void getStatus() {
        studentChecking.setStatus(Status.ACTIVE);

        assertEquals(Status.ACTIVE, studentChecking.getStatus());
    }

    @Test
    void getTransactionsMade() {
        List<LocalDateTime> nullList = new ArrayList<LocalDateTime>();
        studentChecking.setTransactionsMade(nullList);

        assertEquals(0, studentChecking.getTransactionsMade().size());
    }

    @Test
    void getMaxTransferencesInADay() {
        studentChecking.setMaxTransferencesInADay(2);

        assertEquals(2, studentChecking.getMaxTransferencesInADay());
    }
}