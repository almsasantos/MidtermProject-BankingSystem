package com.ironhack.MidtermProject.controller.impl.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.service.users.AccountHolderService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountHolderService accountHolderService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;
    private AccountHolder accountHolder;
    private Address address;
    private StudentChecking studentChecking;
    private List<Account> accounts = new ArrayList<Account>();
    private LoginAccount loginAccount;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address = new Address();
        address.setCountry("Spain");
        address.setCity("Madrid");
        address.setPostalCode("28033");
        address.setStreet("Calle Golfo de Salonica");

        accountHolder = new AccountHolder("Ana", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        accounts.add(studentChecking);

        loginAccount = new LoginAccount();
        loginAccount.setId(1);
        loginAccount.setPassword("pass");

        accountHolder.setAccounts(accounts);

        List<AccountHolder> accountHolderList = Arrays.asList(accountHolder);
        when(accountHolderService.findAll()).thenReturn(accountHolderList);
        when(accountHolderService.findById(1)).thenReturn(accountHolder);
        when(accountHolderService.loginAccountHolder(loginAccount)).thenReturn(accountHolder);
        when(accountHolderService.logOutAccountHolder(loginAccount)).thenReturn(accountHolder);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/accountholders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(accountHolder.getUserId()))
                .andExpect(jsonPath("$[0].name").value(accountHolder.getName()))
                .andExpect(jsonPath("$[0].password").value(accountHolder.getPassword()))
                //.andExpect(jsonPath("$[0].dateOfBirth").value(accountHolder.getDateOfBirth()))
                .andExpect(jsonPath("$[0].mailingAddress").value(accountHolder.getMailingAddress()));
                //.andExpect(jsonPath("$[0].primaryAddress").value(accountHolder.getPrimaryAddress()))
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/accountholders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(accountHolder.getUserId()))
                .andExpect(jsonPath("$[0].name").value(accountHolder.getName()))
                .andExpect(jsonPath("$[0].password").value(accountHolder.getPassword()))
                //.andExpect(jsonPath("$[0].dateOfBirth").value(accountHolder.getDateOfBirth()))
                .andExpect(jsonPath("$[0].mailingAddress").value(accountHolder.getMailingAddress()));
    }

    @Test
    void checkAccountBalance() throws Exception {
        mockMvc.perform(get("/accountholders/balance")
                .param("account_id", String.valueOf(studentChecking.getAccountId()))
                .param("user_id", String.valueOf(accountHolder.getUserId())))
                .andExpect(status().isOk());
        //when(accountHolderService.checkAccountBalance(studentChecking.getAccountId(), accountHolder.getUserId()));
    }

    @Test
    void loginAccountHolder() throws Exception {
        mockMvc.perform(patch("/accountholders/login")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void logoutAccountHolder() throws Exception {
        mockMvc.perform(patch("/accountholders/logout")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createAccountHolder() throws Exception {
        mockMvc.perform(post("/accountholder")
                .content(objectMapper.writeValueAsString(accountHolder))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void transferAmount() {
    }
}