package com.ironhack.MidtermProject.controller.impl.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SavingsService savingsService;

    @Autowired
    private SavingRepository savingRepository;

    private MockMvc mockMvc;
    private Saving saving;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));

        List<Saving> savings = Arrays.asList(saving);
        when(savingsService.findAll()).thenReturn(savings);
        when(savingsService.findById(1)).thenReturn(saving);
        when(savingsService.findByStatus(Status.ACTIVE)).thenReturn(savings);
        when(savingsService.findByMinimumBalance(new BigDecimal("1000"))).thenReturn(savings);
        when(savingsService.findByInterestRate(new BigDecimal("0.025"))).thenReturn(savings);
        when(savingsService.findByDate(LocalDate.now())).thenReturn(savings);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/savings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(saving.getAccountId()))
                .andExpect(jsonPath("$[0].secretKey").value(saving.getSecretKey()))
                //.andExpect(jsonPath("$[0].status").value(saving.getStatus()))
                //.andExpect(jsonPath("$[0].accountType").value(saving.getAccountType()))
                .andExpect(jsonPath("$[0].transactionsMade").value(saving.getTransactionsMade()))
                .andExpect(jsonPath("$[0].primaryOwner").value(saving.getPrimaryOwner()))
                .andExpect(jsonPath("$[0].secondaryOwner").value(saving.getSecondaryOwner()))
                //.andExpect(jsonPath("$[0].balance").value(saving.getBalance()))
                .andExpect(jsonPath("$[0].maxTransferencesInADay").value(saving.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$[0].penaltyFee").value(saving.getPenaltyFee()))
                .andExpect(jsonPath("$[0].minimumBalance").value(saving.getMinimumBalance()))
                .andExpect(jsonPath("$[0].interestRate").value(saving.getInterestRate()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/savings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(saving.getAccountId()))
                .andExpect(jsonPath("$.secretKey").value(saving.getSecretKey()))
                //.andExpect(jsonPath("$.status").value(saving.getStatus()))
                //.andExpect(jsonPath("$.accountType").value(saving.getAccountType()))
                .andExpect(jsonPath("$.transactionsMade").value(saving.getTransactionsMade()))
                .andExpect(jsonPath("$.primaryOwner").value(saving.getPrimaryOwner()))
                .andExpect(jsonPath("$.secondaryOwner").value(saving.getSecondaryOwner()))
                //.andExpect(jsonPath("$.balance").value(saving.getBalance()))
                .andExpect(jsonPath("$.maxTransferencesInADay").value(saving.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$.penaltyFee").value(saving.getPenaltyFee()))
                .andExpect(jsonPath("$.minimumBalance").value(saving.getMinimumBalance()))
                .andExpect(jsonPath("$.interestRate").value(saving.getInterestRate()));
    }

    @Test
    void findByStatus() throws Exception {
        mockMvc.perform(get("/savings/status")
                .param("status", String.valueOf("ACTIVE")))
                .andExpect(status().isOk());
    }

    @Test
    void findByMinimumBalance() throws Exception {
        BigDecimal minimum = new BigDecimal("1000");
        mockMvc.perform(get("/savings/minimum-balance")
                .param("minimum_balance", String.valueOf(minimum)))
                .andExpect(status().isOk());
    }

    @Test
    void findByInterestRate() throws Exception {
        BigDecimal interest = new BigDecimal("0.025");
        mockMvc.perform(get("/savings/interest-rate")
                .param("interest_rate", String.valueOf(interest)))
                .andExpect(status().isOk());
    }

}