package com.ironhack.MidtermProject.controller.impl.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import com.ironhack.MidtermProject.service.accounts.CheckingService;
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
class CheckingControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CheckingService checkingService;

    @Autowired
    private CheckingRepository checkingRepository;

    private MockMvc mockMvc;
    private Checking checking;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));

        List<Checking> checkingList = Arrays.asList(checking);
        when(checkingService.findAll()).thenReturn(checkingList);
        when(checkingService.findById(1)).thenReturn(checking);
        when(checkingService.findByStatus(Status.ACTIVE)).thenReturn(checkingList);
        when(checkingService.findByMinimumBalance(new BigDecimal("250"))).thenReturn(checkingList);
        when(checkingService.findByMonthlyMaintenanceFee(new BigDecimal("12"))).thenReturn(checkingList);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/checking-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(checking.getAccountId()))
                .andExpect(jsonPath("$[0].secretKey").value(checking.getSecretKey()))
                //.andExpect(jsonPath("$[0].status").value(checking.getStatus()))
                //.andExpect(jsonPath("$[0].accountType").value(checking.getAccountType()))
                .andExpect(jsonPath("$[0].transactionsMade").value(checking.getTransactionsMade()))
                .andExpect(jsonPath("$[0].primaryOwner").value(checking.getPrimaryOwner()))
                .andExpect(jsonPath("$[0].secondaryOwner").value(checking.getSecondaryOwner()))
                //.andExpect(jsonPath("$[0].balance").value(checking.getBalance()))
                .andExpect(jsonPath("$[0].maxTransferencesInADay").value(checking.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$[0].penaltyFee").value(checking.getPenaltyFee()))
                .andExpect(jsonPath("$[0].minimumBalance").value(checking.getMinimumBalance()))
                .andExpect(jsonPath("$[0].monthlyMaintenanceFee").value(checking.getMonthlyMaintenanceFee()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/checking-accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(checking.getAccountId()))
                .andExpect(jsonPath("$.secretKey").value(checking.getSecretKey()))
                //.andExpect(jsonPath("$.status").value(checking.getStatus()))
                //.andExpect(jsonPath("$.accountType").value(checking.getAccountType()))
                .andExpect(jsonPath("$.transactionsMade").value(checking.getTransactionsMade()))
                .andExpect(jsonPath("$.primaryOwner").value(checking.getPrimaryOwner()))
                .andExpect(jsonPath("$.secondaryOwner").value(checking.getSecondaryOwner()))
                //.andExpect(jsonPath("$.balance").value(checking.getBalance()))
                .andExpect(jsonPath("$.maxTransferencesInADay").value(checking.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$.penaltyFee").value(checking.getPenaltyFee()))
                .andExpect(jsonPath("$.minimumBalance").value(checking.getMinimumBalance()))
                .andExpect(jsonPath("$.monthlyMaintenanceFee").value(checking.getMonthlyMaintenanceFee()));
    }

    @Test
    void findByStatus() throws Exception {
        mockMvc.perform(get("/checking-accounts/status")
                .param("status", String.valueOf("ACTIVE")))
                .andExpect(status().isOk());
    }

    @Test
    void findByMinimumBalance() throws Exception {
        mockMvc.perform(get("/checking-accounts/minimum-balance")
                .param("minimum_balance", String.valueOf(checking.getMinimumBalance())))
                .andExpect(status().isOk());
    }

    @Test
    void findByMonthlyMaintenanceFee() throws Exception {
        mockMvc.perform(get("/checking-accounts/monthly-maintenance-fee")
                .param("monthly_maintenance_fee", String.valueOf(checking.getMonthlyMaintenanceFee())))
                .andExpect(status().isOk());
    }
}