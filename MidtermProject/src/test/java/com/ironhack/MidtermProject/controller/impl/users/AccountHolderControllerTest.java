package com.ironhack.MidtermProject.controller.impl.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
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

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AdminRepository adminRepository;

    private MockMvc mockMvc;
    private AccountHolder accountHolder;
    private AccountHolder accountHolder1;
    private Address address;
    private Admin admin;
    private StudentChecking studentChecking;
    private List<Account> accounts = new ArrayList<Account>();
    private LoginAccount loginAccount;
    private LoginAccount loginAccount1;
    private Transference transference = new Transference();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address = new Address();
        address.setCountry("Spain");
        address.setCity("Madrid");
        address.setPostalCode("28033");
        address.setStreet("Calle Golfo de Salonica");

        accountHolder = new AccountHolder("Ana Martins", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");
        accountHolder.login();

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        accounts.add(studentChecking);

        loginAccount = new LoginAccount();
        loginAccount.setId(1);
        loginAccount.setPassword("pass");

        admin = new Admin("Gabriel Mars", "pass");
        admin.setUserId(7);

        loginAccount1 = new LoginAccount();
        loginAccount1.setId(7);
        loginAccount1.setPassword("pass");

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
                .andExpect(jsonPath("$[0].mailingAddress").value(accountHolder.getMailingAddress()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/accountholders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(accountHolder.getUserId()))
                .andExpect(jsonPath("$.name").value(accountHolder.getName()))
                .andExpect(jsonPath("$.password").value(accountHolder.getPassword()))
                .andExpect(jsonPath("$.mailingAddress").value(accountHolder.getMailingAddress()));
    }

    @Test
    void checkAccountBalance() throws Exception {
        mockMvc.perform(get("/accountholders/balance")
                .param("account_id", String.valueOf(2))
                .param("user_id", String.valueOf(1)))
                .andExpect(status().isOk());
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
        accountHolder1 = new AccountHolder();
        accountHolder1.setName("Ana Santos");
        accountHolder1.setPassword("pass");
        accountHolder1.setPrimaryAddress(address);

        mockMvc.perform(post("/accountholder")
                .param("admin_id", String.valueOf(admin.getUserId()))
                .content(objectMapper.writeValueAsString(accountHolder1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void transferAmount() throws Exception {
        transference.setUserId(1);
        transference.setSenderName("Ana Martins");
        transference.setSenderAccountId(2);
        transference.setReceiverName("Ana Martins");
        transference.setReceiverAccountId(2);
        transference.setAmountToTransfer(new BigDecimal("10"));

        mockMvc.perform(patch("/transference")
                .content(objectMapper.writeValueAsString(transference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}