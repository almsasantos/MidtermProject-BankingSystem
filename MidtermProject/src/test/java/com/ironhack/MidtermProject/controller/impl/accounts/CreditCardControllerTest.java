package com.ironhack.MidtermProject.controller.impl.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
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
class CreditCardControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private MockMvc mockMvc;
    private CreditCard creditCard;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));

        List<CreditCard> creditCardList = Arrays.asList(creditCard);
        when(creditCardService.findAll()).thenReturn(creditCardList);
        when(creditCardService.findById(1)).thenReturn(creditCard);
        when(creditCardService.findByInterestRate(new BigDecimal("0.2"))).thenReturn(creditCardList);
        when(creditCardService.findByDate(LocalDate.now())).thenReturn(creditCardList);
        when(creditCardService.findByCreditLimit(new BigDecimal("100"))).thenReturn(creditCardList);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/credit-cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(creditCard.getAccountId()))
                .andExpect(jsonPath("$[0].primaryOwner").value(creditCard.getPrimaryOwner()))
                .andExpect(jsonPath("$[0].secondaryOwner").value(creditCard.getSecondaryOwner()))
                .andExpect(jsonPath("$[0].maxTransferencesInADay").value(creditCard.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$[0].penaltyFee").value(creditCard.getPenaltyFee()))
                .andExpect(jsonPath("$[0].creditLimit").value(creditCard.getCreditLimit()))
                .andExpect(jsonPath("$[0].interestRate").value(creditCard.getInterestRate()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/credit-cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(creditCard.getAccountId()))
                .andExpect(jsonPath("$.primaryOwner").value(creditCard.getPrimaryOwner()))
                .andExpect(jsonPath("$.secondaryOwner").value(creditCard.getSecondaryOwner()))
                .andExpect(jsonPath("$.maxTransferencesInADay").value(creditCard.getMaxTransferencesInADay()))
                .andExpect(jsonPath("$.penaltyFee").value(creditCard.getPenaltyFee()))
                .andExpect(jsonPath("$.creditLimit").value(creditCard.getCreditLimit()))
                .andExpect(jsonPath("$.interestRate").value(creditCard.getInterestRate()));
    }

    @Test
    void findByCreditLimit() throws Exception {
        BigDecimal credit = new BigDecimal("100");
        mockMvc.perform(get("/credit-cards/credit-limit")
                .param("credit_limit", String.valueOf(credit)))
                .andExpect(status().isOk());
    }

    @Test
    void findByInterestRate() throws Exception {
        BigDecimal interest = new BigDecimal("0.2");
        mockMvc.perform(get("/credit-cards/interest-rate")
                .param("interest_rate", String.valueOf(interest)))
                .andExpect(status().isOk());
    }
}