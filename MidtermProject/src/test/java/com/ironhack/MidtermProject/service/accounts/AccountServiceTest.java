package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    private StudentChecking studentChecking;
    private List<Account> studentCheckingList = new ArrayList<Account>();

    @BeforeEach
    void setUp() {
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentCheckingList.add(studentChecking);

        when(accountRepository.findAll()).thenReturn(studentCheckingList);
    }

    @Test
    void findAll() {
        assertEquals(studentCheckingList, accountService.findAll());
    }
}