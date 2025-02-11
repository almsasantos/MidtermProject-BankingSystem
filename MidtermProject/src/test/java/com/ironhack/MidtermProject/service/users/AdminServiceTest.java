package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import com.ironhack.MidtermProject.repository.users.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private Admin admin;
    private LoginAccount loginAccount;
    private AccountHolder accountHolder;
    private Checking checking;
    private Saving saving;
    private CreditCard creditCard;
    private StudentChecking studentChecking;
    private List<Account> accountsHolderList = new ArrayList<Account>();
    private Address address;
    private SavingViewModel savingViewModel = new SavingViewModel();
    private CreditCardViewModel creditCardViewModel = new CreditCardViewModel();
    private AccountViewModel accountViewModel = new AccountViewModel();
    private CreateThirdParty createThirdParty = new CreateThirdParty();
    private ThirdParty thirdParty = new ThirdParty();
    private ChangeBalance changeBalance;

    @BeforeEach
    void setUp() {
        admin = new Admin("Mickael Mickael", "pass");
        adminRepository.save(admin);

        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.FROZEN, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving);

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.FROZEN, new BigDecimal("250"), new BigDecimal("12"));
        checkingRepository.save(checking);

        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.FROZEN);
        studentCheckingRepository.save(studentChecking);

        changeBalance = new ChangeBalance();

        accountHolder = new AccountHolder("Ana Santos", "pass", LocalDate.of(1990, 8, 19), address);
        accountsHolderList.add(checking);
        accountsHolderList.add(creditCard);
        accountsHolderList.add(saving);
        accountsHolderList.add(studentChecking);
        accountHolder.setAccounts(accountsHolderList);
        accountHolderRepository.save(accountHolder);

        saving.setPrimaryOwner(accountHolder);
        savingRepository.save(saving);

        creditCard.setPrimaryOwner(accountHolder);
        creditCardRepository.save(creditCard);

        studentChecking.setPrimaryOwner(accountHolder);
        studentCheckingRepository.save(studentChecking);

        checking.setPrimaryOwner(accountHolder);
        checkingRepository.save(checking);

        loginAccount = new LoginAccount();
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        checkingRepository.deleteAll();
        savingRepository.deleteAll();
        creditCardRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        usersRepository.deleteAll();
        loginAccount = null;
        address = null;
    }

    @Test
    void findAll() {
        assertEquals(1,  adminService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(admin, adminService.findById(admin.getUserId()));
    }

    @Test
    void findByName() {
        assertEquals(1, adminService.findByName("Mickael Mickael").size());
    }

    @Test
    void createNewAdmin() {
        Admin admin1 = new Admin("Maria Silva", "password");
        adminService.createNewAdmin(admin1);
        assertEquals(2, adminRepository.findAll().size());
    }

    @Test
    void loginAdmin() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);
        admin.login();
        assertEquals(true, admin.isLogged());
    }

    @Test
    void loginAdminPasswordIncorrect() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword("hola");
        assertThrows(DataNotFoundException.class, () -> {
            adminService.loginAdmin(loginAccount);});
    }

    @Test
    void logOutAdmin() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        admin.login();
        adminRepository.save(admin);
        adminService.logOutAdmin(loginAccount);
        admin.logOut();
        adminRepository.save(admin);
        assertEquals(false, admin.isLogged());
    }

    @Test
    void adminIsLogged() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword("hola");
        assertThrows(DataNotFoundException.class, () -> {
            adminService.logOutAdmin(loginAccount);});
    }

    @Test
    void createNewSavingsAccount() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        savingViewModel.setBalance(new BigDecimal("900"));
        savingViewModel.setSecretKey("000000");
        savingViewModel.setPrimaryOwnerId(accountHolder.getUserId());
        savingViewModel.setStatus(Status.ACTIVE);
        savingViewModel.setInterestRate(new BigDecimal("0.025"));
        savingViewModel.setMinimumBalance(new BigDecimal("1000"));

        adminService.createNewSavingsAccount(admin.getUserId(), savingViewModel);

        assertEquals(2, savingRepository.findAll().size());
    }

    @Test
    void createNewAccountDepAge() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        accountViewModel.setPrimaryOwnerId(accountHolder.getUserId());
        accountViewModel.setSecretKey("000000");
        accountViewModel.setSecondaryOwnerId(null);
        accountViewModel.setStatus(Status.ACTIVE);
        accountViewModel.setBalance(new BigDecimal("500"));

        adminService.createNewAccountDepAge(admin.getUserId(), accountViewModel);

        assertEquals(2, checkingRepository.findAll().size());
    }

    @Test
    void createNewStudentChecking() {
        accountViewModel.setPrimaryOwnerId(accountHolder.getUserId());
        accountViewModel.setSecretKey("000000");
        accountViewModel.setSecondaryOwnerId(null);
        accountViewModel.setStatus(Status.ACTIVE);
        accountViewModel.setBalance(new BigDecimal("500"));
        adminService.createNewStudentChecking(accountHolder, accountViewModel, null);
        assertEquals(2, studentCheckingRepository.findAll().size());
    }

    @Test
    void createNewCheckingAccount() {
        accountViewModel.setPrimaryOwnerId(accountHolder.getUserId());
        accountViewModel.setSecretKey("000000");
        accountViewModel.setSecondaryOwnerId(null);
        accountViewModel.setStatus(Status.ACTIVE);
        accountViewModel.setBalance(new BigDecimal("500"));
        adminService.createNewCheckingAccount(accountHolder, accountViewModel, null);
        assertEquals(2, checkingRepository.findAll().size());
    }

    @Test
    void createNewCreditCardAccount() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        creditCardViewModel.setBalance(new BigDecimal("900"));
        creditCardViewModel.setPrimaryOwnerId(accountHolder.getUserId());
        creditCardViewModel.setInterestRate(new BigDecimal("0.025"));
        creditCardViewModel.setCreditLimit(new BigDecimal("100"));

        adminService.createNewCreditCardAccount(admin.getUserId(), creditCardViewModel);

        assertEquals(2, creditCardRepository.findAll().size());
    }

    @Test
    void checkAccountBalance() {
        admin.login();
        adminRepository.save(admin);
        assertEquals(saving.getBalance().getAmount(), adminService.checkAccountBalance(admin.getUserId(), saving.getAccountId()));
        assertEquals(checking.getBalance().getAmount(), adminService.checkAccountBalance(admin.getUserId(), checking.getAccountId()));
        assertEquals(creditCard.getBalance().getAmount(), adminService.checkAccountBalance(admin.getUserId(), creditCard.getAccountId()));
        assertEquals(studentChecking.getBalance().getAmount(), adminService.checkAccountBalance(admin.getUserId(), studentChecking.getAccountId()));
    }

    @Test
    void debitBalanceSavings() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(saving.getAccountId());

        adminService.debitBalance(admin.getUserId(), changeBalance);

        saving.setBalance(new Money(saving.getBalance().decreaseAmount(changeBalance.getAmount())));
        savingRepository.save(saving);

        assertEquals(new BigDecimal("890.00"), saving.getBalance().getAmount());
    }

    @Test
    void debitBalanceCreditCard() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(creditCard.getAccountId());

        adminService.debitBalance(admin.getUserId(), changeBalance);

        creditCard.setBalance(new Money(creditCard.getBalance().decreaseAmount(changeBalance.getAmount())));
        creditCardRepository.save(creditCard);

        assertEquals(new BigDecimal("890.00"), creditCard.getBalance().getAmount());
    }

    @Test
    void debitBalanceChecking() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(checking.getAccountId());

        adminService.debitBalance(admin.getUserId(), changeBalance);

        checking.setBalance(new Money(checking.getBalance().decreaseAmount(changeBalance.getAmount())));
        checkingRepository.save(checking);

        assertEquals(new BigDecimal("890.00"), checking.getBalance().getAmount());
    }

    @Test
    void debitBalanceStudentChecking() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(studentChecking.getAccountId());

        adminService.debitBalance(admin.getUserId(), changeBalance);

        studentChecking.setBalance(new Money(studentChecking.getBalance().decreaseAmount(changeBalance.getAmount())));
        studentCheckingRepository.save(studentChecking);

        assertEquals(new BigDecimal("90.00"), studentChecking.getBalance().getAmount());
    }

    @Test
    void creditBalanceSavings() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(saving.getAccountId());
        adminService.creditBalance(admin.getUserId(), changeBalance);

        saving.setBalance(new Money(saving.getBalance().increaseAmount(changeBalance.getAmount())));
        savingRepository.save(saving);

        assertEquals(new BigDecimal("910.00"), saving.getBalance().getAmount());
    }

    @Test
    void creditBalanceCreditCard() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(creditCard.getAccountId());
        adminService.creditBalance(admin.getUserId(), changeBalance);

        creditCard.setBalance(new Money(creditCard.getBalance().increaseAmount(changeBalance.getAmount())));
        creditCardRepository.save(creditCard);

        assertEquals(new BigDecimal("910.00"), creditCard.getBalance().getAmount());
    }

    @Test
    void creditBalanceChecking() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(checking.getAccountId());
        adminService.creditBalance(admin.getUserId(), changeBalance);

        checking.setBalance(new Money(checking.getBalance().increaseAmount(changeBalance.getAmount())));
        checkingRepository.save(checking);

        assertEquals(new BigDecimal("910.00"), checking.getBalance().getAmount());
    }

    @Test
    void creditBalanceStudentChecking() {
        admin.login();
        adminRepository.save(admin);

        changeBalance.setAmount(new BigDecimal("10"));
        changeBalance.setAccountOwnerName(accountHolder.getName());
        changeBalance.setOwnerId(accountHolder.getUserId());
        changeBalance.setAccountId(studentChecking.getAccountId());
        adminService.creditBalance(admin.getUserId(), changeBalance);

        studentChecking.setBalance(new Money(studentChecking.getBalance().increaseAmount(changeBalance.getAmount())));
        studentCheckingRepository.save(studentChecking);

        assertEquals(new BigDecimal("110.00"), studentChecking.getBalance().getAmount());
    }

    @Test
    void createNewThirdParty() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        createThirdParty.setName("Gabriel Perez");
        createThirdParty.setPassword("gaby");
        adminService.createNewThirdParty(admin.getUserId(), createThirdParty);
        assertEquals(1, thirdPartyRepository.findAll().size());
    }

    @Test
    void deleteAccount() {
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        adminService.deleteAccount(saving.getAccountId(), admin.getUserId());
        assertEquals(3, accountRepository.findAll().size());
    }

    @Test
    void unfreezeAccount(){
        loginAccount.setId(admin.getUserId());
        loginAccount.setPassword(admin.getPassword());
        adminService.loginAdmin(loginAccount);

        adminService.unfreezeAccount(admin.getUserId(), saving.getAccountId());
        adminService.unfreezeAccount(admin.getUserId(), checking.getAccountId());
        adminService.unfreezeAccount(admin.getUserId(), studentChecking.getAccountId());

        assertEquals(Status.FROZEN, saving.getStatus());
        assertEquals(Status.FROZEN, checking.getStatus());
        assertEquals(Status.FROZEN, studentChecking.getStatus());
    }
}