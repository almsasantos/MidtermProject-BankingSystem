package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentCheckingServiceTest {
    @Autowired
    private StudentCheckingService studentCheckingService;

    @MockBean
    private StudentCheckingRepository studentCheckingRepository;

    private StudentChecking studentChecking;
    private List<StudentChecking> studentCheckingList;

    @BeforeEach
    void setUp() {
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentChecking.setAccountId(1);

        studentCheckingList = new ArrayList<StudentChecking>();
        studentCheckingList.add(studentChecking);
        when(studentCheckingRepository.findAll()).thenReturn(studentCheckingList);
        when(studentCheckingRepository.findById(studentChecking.getAccountId())).thenReturn(Optional.of(studentChecking));
        when(studentCheckingRepository.findByStatus(studentChecking.getStatus())).thenReturn(studentCheckingList);
    }

    @Test
    void findAll() {
        assertEquals(studentCheckingList, studentCheckingService.findAll());
    }

    @Test
    void findById() {
        assertEquals(studentChecking, studentCheckingService.findById(studentChecking.getAccountId()));
    }

    @Test
    void findByStatus() {
        assertEquals(studentCheckingList, studentCheckingService.findByStatus(studentChecking.getStatus()));
    }
}