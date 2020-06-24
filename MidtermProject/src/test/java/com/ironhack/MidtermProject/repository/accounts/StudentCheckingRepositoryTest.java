package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentCheckingRepositoryTest {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private StudentChecking studentChecking;

    @BeforeEach
    void setUp() {
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentCheckingRepository.save(studentChecking);
    }

    @AfterEach
    void tearDown() {
        studentCheckingRepository.deleteAll();
    }

    @Test
    void findBySecretKey() {
        assertEquals(1, studentCheckingRepository.findBySecretKey("000000").size());
    }

    @Test
    void findByStatus() {
        assertEquals(1, studentCheckingRepository.findByStatus(Status.ACTIVE).size());
    }
}