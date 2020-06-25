package com.ironhack.MidtermProject.controller.impl.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import com.ironhack.MidtermProject.service.users.ThirdPartyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @MockBean
    private ThirdPartyService thirdPartyService;

    private MockMvc mockMvc;
    private ThirdParty thirdParty;
    private CreateThirdParty createThirdParty;
    private Admin admin;
    private HashMap<String, String> thirdPartyDetails = new HashMap<String, String>();
    private LoginAccount loginAccount;
    private ThirdPartyTransaction thirdPartyTransaction;
    private Checking checking;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        thirdParty = new ThirdParty("Ana", "pass");
        thirdPartyDetails.put("1234", "Toyota");
        thirdParty.setAccountDetails(thirdPartyDetails);

        loginAccount = new LoginAccount();
        loginAccount.setId(1);
        loginAccount.setPassword("pass");

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));

        thirdPartyTransaction = new ThirdPartyTransaction();
        thirdPartyTransaction.setAccountId(checking.getAccountId());
        thirdPartyTransaction.setSecretKey(checking.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));

        List<ThirdParty> thirdPartyList = Arrays.asList(thirdParty);
        when(thirdPartyService.findAll()).thenReturn(thirdPartyList);
        when(thirdPartyService.findById(1)).thenReturn(thirdParty);
        when(thirdPartyService.loginThirdParty(loginAccount)).thenReturn(thirdParty);
        when(thirdPartyService.logOutThirdParty(loginAccount)).thenReturn(thirdParty);
        thirdPartyService.debitTransaction("1234", thirdPartyTransaction);
        thirdPartyService.creditTransaction("1234", thirdPartyTransaction);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/thirdparties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(thirdParty.getUserId()))
                .andExpect(jsonPath("$[0].name").value(thirdParty.getName()))
                .andExpect(jsonPath("$[0].password").value(thirdParty.getPassword()))
                .andExpect(jsonPath("$[0].accountDetails").value(thirdParty.getAccountDetails()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/thirdparties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(thirdParty.getUserId()))
                .andExpect(jsonPath("$.name").value(thirdParty.getName()))
                .andExpect(jsonPath("$.password").value(thirdParty.getPassword()))
                .andExpect(jsonPath("$.accountDetails").value(thirdParty.getAccountDetails()));
    }

    @Test
    void loginThirdParty() throws Exception {
        mockMvc.perform(patch("/thirdparties/login")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(thirdParty.getUserId()))
                .andExpect(jsonPath("$.name").value(thirdParty.getName()))
                .andExpect(jsonPath("$.password").value(thirdParty.getPassword()))
                .andExpect(jsonPath("$.accountDetails").value(thirdParty.getAccountDetails()));
    }

    @Test
    void logoutThirdParty() throws Exception {
        mockMvc.perform(patch("/thirdparties/logout")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(thirdParty.getUserId()))
                .andExpect(jsonPath("$.name").value(thirdParty.getName()))
                .andExpect(jsonPath("$.password").value(thirdParty.getPassword()))
                .andExpect(jsonPath("$.accountDetails").value(thirdParty.getAccountDetails()));
    }

    @Test
    void debitTransaction() throws Exception {
        mockMvc.perform(patch("/debit-transaction").param("hashed_key", "1234"))
                .andExpect(status().isNoContent());
    }

    @Test
    void creditTransaction() throws Exception {
        mockMvc.perform(patch("/credit-transaction").param("hashed_key", "1234"))
                .andExpect(status().isNoContent());
    }
}