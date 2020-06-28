package com.ironhack.MidtermProject.controller.impl.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
import com.ironhack.MidtermProject.service.accounts.StudentCheckingService;
import com.ironhack.MidtermProject.service.users.AccountHolderService;
import com.ironhack.MidtermProject.service.users.AdminService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminRepository adminRepository;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AccountHolderService accountHolderService;

    @MockBean
    private StudentCheckingService studentCheckingService;

    private MockMvc mockMvc;
    private Admin admin;
    private Admin admin1;
    private CreateThirdParty createThirdParty;
    private LoginAccount loginAccount;
    private StudentChecking studentChecking;
    private SavingViewModel savingViewModel;
    private AccountViewModel accountViewModel;
    private CreditCardViewModel creditCardViewModel;
    private ChangeBalance changeBalance;
    private AccountHolder accountHolder;
    private Address address;
    HashMap<String, String> hashed = new HashMap<String, String>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        admin = new Admin("Ana Santos", "pass");
        admin.login();

        loginAccount = new LoginAccount();
        loginAccount.setId(1);
        loginAccount.setPassword("pass");

        address = new Address();
        address.setCountry("Spain");
        address.setCity("Madrid");
        address.setPostalCode("28033");
        address.setStreet("Calle Golfo de Salonica");

        accountHolder = new AccountHolder("Ana Martins", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.FROZEN);
        studentChecking.setPrimaryOwner(accountHolder);

        changeBalance = new ChangeBalance();
        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountId(3);
        changeBalance.setOwnerId(2);
        changeBalance.setAccountOwnerName(accountHolder.getName());

        List<Admin> adminList = Arrays.asList(admin);
        when(adminService.findAll()).thenReturn(adminList);
        when(adminService.findById(1)).thenReturn(admin);
        when(adminService.findByName(admin.getName())).thenReturn(adminList);
        when(adminService.checkAccountBalance(admin.getUserId(), studentChecking.getAccountId())).thenReturn(studentChecking.getBalance().getAmount());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(admin.getUserId()))
                .andExpect(jsonPath("$[0].password").value(admin.getPassword()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(admin.getUserId()))
                .andExpect(jsonPath("$.password").value(admin.getPassword()));
    }

    @Test
    void findByName() throws Exception {
        mockMvc.perform(get("/admins-name/Ana Santos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(admin.getUserId()))
                .andExpect(jsonPath("$[0].name").value(admin.getName()))
                .andExpect(jsonPath("$[0].password").value(admin.getPassword()));
    }

    @Test
    void unfreezeAccount() throws Exception {
        mockMvc.perform(get("/admins/unfreeze-account")
                .param("admin_id", String.valueOf(1))
                .param("account_id", String.valueOf(2)))
                .andExpect(status().isOk());
    }

    @Test
    void loginAdmin() throws Exception {
        mockMvc.perform(patch("/admins/login")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void logoutAdmin() throws Exception {
        mockMvc.perform(patch("/admins/logout")
                .content(objectMapper.writeValueAsString(loginAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void checkAccountBalance() throws Exception {
        mockMvc.perform(get("/admin/balance")
                .param("admin_id", String.valueOf(1))
                .param("account_id", String.valueOf(2)))
                .andExpect(status().isOk());
    }

    @Test
    void createNewAdmin() throws Exception {
        admin1 = new Admin("Mickael Verde", "pass");
        mockMvc.perform(post("/admin")
                .content(objectMapper.writeValueAsString(admin1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewThirdParty() throws Exception {
        createThirdParty = new CreateThirdParty();
        createThirdParty.setName("Ana");
        createThirdParty.setPassword("pass");
        createThirdParty.setHashedName("tesla");

        mockMvc.perform(post("/third-party")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(createThirdParty))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createSavingsAccount() throws Exception {
        savingViewModel = new SavingViewModel();
        savingViewModel.setPrimaryOwnerId(2);
        savingViewModel.setMinimumBalance(new BigDecimal("200"));
        savingViewModel.setInterestRate(new BigDecimal("0.2"));
        savingViewModel.setStatus(Status.ACTIVE);
        savingViewModel.setBalance(new BigDecimal("10000"));
        savingViewModel.setSecretKey("000000");

        mockMvc.perform(post("/account/savings")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(savingViewModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createCreditCardAccount() throws Exception {
        creditCardViewModel = new CreditCardViewModel();
        creditCardViewModel.setPrimaryOwnerId(2);
        creditCardViewModel.setInterestRate(new BigDecimal("0.2"));
        creditCardViewModel.setCreditLimit(new BigDecimal("100"));
        creditCardViewModel.setBalance(new BigDecimal("10000"));

        mockMvc.perform(post("/account/credit-card")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(creditCardViewModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewAccount() throws Exception {
        accountViewModel = new AccountViewModel();
        accountViewModel.setPrimaryOwnerId(2);
        accountViewModel.setBalance(new BigDecimal("10000"));

        mockMvc.perform(post("/account/depending-age")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(accountViewModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void debitBalance() throws Exception {
        mockMvc.perform(patch("/debit-balance")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(changeBalance))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void creditBalance() throws Exception {
        mockMvc.perform(patch("/credit-balance")
                .param("admin_id", String.valueOf(1))
                .content(objectMapper.writeValueAsString(changeBalance))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAccount() throws Exception {
        mockMvc.perform(delete("/account")
                .param("account_id", String.valueOf(3))
                .param("admin_id", String.valueOf(1)))
                .andExpect(status().isNoContent());
    }
}