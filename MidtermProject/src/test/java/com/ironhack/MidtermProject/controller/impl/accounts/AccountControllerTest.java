package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import com.ironhack.MidtermProject.service.accounts.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private MockMvc mockMvc;
    private StudentChecking studentChecking;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);

        List<Account> accountList = Arrays.asList(studentChecking);
        when(accountService.findAll()).thenReturn(accountList);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(studentChecking.getAccountId()))
                .andExpect(jsonPath("$[0].primaryOwner").value(studentChecking.getPrimaryOwner()))
                .andExpect(jsonPath("$[0].secondaryOwner").value(studentChecking.getSecondaryOwner()))
                //.andExpect(jsonPath("$[0].balance").value(studentChecking.getBalance()))
                .andExpect(jsonPath("$[0].penaltyFee").value(studentChecking.getPenaltyFee()));

    }
}