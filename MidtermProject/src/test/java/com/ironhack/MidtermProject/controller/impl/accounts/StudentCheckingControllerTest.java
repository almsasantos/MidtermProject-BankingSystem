package com.ironhack.MidtermProject.controller.impl.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import com.ironhack.MidtermProject.service.accounts.StudentCheckingService;
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
class StudentCheckingControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private StudentCheckingService studentCheckingService;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private MockMvc mockMvc;
    private StudentChecking studentChecking;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);

        List<StudentChecking> studentCheckingList= Arrays.asList(studentChecking);
        when(studentCheckingService.findAll()).thenReturn(studentCheckingList);
        when(studentCheckingService.findById(1)).thenReturn(studentChecking);
        when(studentCheckingService.findByStatus(Status.ACTIVE)).thenReturn(studentCheckingList);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/student-checking-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(studentChecking.getAccountId()))
                .andExpect(jsonPath("$[0].secretKey").value(studentChecking.getSecretKey()))
                //.andExpect(jsonPath("$[0].status").value(studentChecking.getStatus()))
                //.andExpect(jsonPath("$[0].accountType").value(studentChecking.getAccountType()))
                .andExpect(jsonPath("$[0].transactionsMade").value(studentChecking.getTransactionsMade()))
                .andExpect(jsonPath("$[0].primaryOwner").value(studentChecking.getPrimaryOwner()))
                .andExpect(jsonPath("$[0].secondaryOwner").value(studentChecking.getSecondaryOwner()))
                //.andExpect(jsonPath("$[0].balance").value(studentChecking.getBalance()))
                .andExpect(jsonPath("$[0].maxTransferencesInADay").value(studentChecking.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$[0].penaltyFee").value(studentChecking.getPenaltyFee()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/student-checking-accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(studentChecking.getAccountId()))
                .andExpect(jsonPath("$.secretKey").value(studentChecking.getSecretKey()))
                //.andExpect(jsonPath("$.status").value(studentChecking.getStatus()))
                //.andExpect(jsonPath("$.accountType").value(studentChecking.getAccountType()))
                .andExpect(jsonPath("$.transactionsMade").value(studentChecking.getTransactionsMade()))
                .andExpect(jsonPath("$.primaryOwner").value(studentChecking.getPrimaryOwner()))
                .andExpect(jsonPath("$.secondaryOwner").value(studentChecking.getSecondaryOwner()))
                //.andExpect(jsonPath("$.balance").value(studentChecking.getBalance()))
                .andExpect(jsonPath("$.maxTransferencesInADay").value(studentChecking.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$.penaltyFee").value(studentChecking.getPenaltyFee()));
    }

    @Test
    void findByStatus() throws Exception {
        mockMvc.perform(get("/student-checking-accounts/status")
                .param("status", String.valueOf("ACTIVE")))
                .andExpect(status().isOk());
    }
}