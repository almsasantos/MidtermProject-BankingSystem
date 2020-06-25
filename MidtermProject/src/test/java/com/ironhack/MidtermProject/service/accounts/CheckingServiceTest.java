package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
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
class CheckingServiceTest {
    @Autowired
    private CheckingService checkingService;

    @MockBean
    private CheckingRepository checkingRepository;

    private Checking checking;
    private List<Checking> checkingList = new ArrayList<Checking>();

    @BeforeEach
    void setUp() {
        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checkingList.add(checking);

        when(checkingRepository.findAll()).thenReturn(checkingList);
        when(checkingRepository.findById(checking.getAccountId())).thenReturn(Optional.of(checking));
        when(checkingRepository.findByStatus(checking.getStatus())).thenReturn(checkingList);
        when(checkingRepository.findByMinimumBalance(checking.getMinimumBalance())).thenReturn(checkingList);
        when(checkingRepository.findByMonthlyMaintenanceFee(checking.getMonthlyMaintenanceFee())).thenReturn(checkingList);
    }

    @Test
    void findAll() {
        assertEquals(checkingList, checkingService.findAll());
    }

    @Test
    void findById() {
        assertEquals(checking, checkingService.findById(checking.getAccountId()));
    }

    @Test
    void findByStatus() {
        assertEquals(checkingList, checkingService.findByStatus(checking.getStatus()));
    }

    @Test
    void findByMinimumBalance() {
        assertEquals(checkingList, checkingService.findByMinimumBalance(checking.getMinimumBalance()));
    }

    @Test
    void findByMonthlyMaintenanceFee() {
        assertEquals(checkingList, checkingService.findByMonthlyMaintenanceFee(checking.getMonthlyMaintenanceFee()));
    }
}