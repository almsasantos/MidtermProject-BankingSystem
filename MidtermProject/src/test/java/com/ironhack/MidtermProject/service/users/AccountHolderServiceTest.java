package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountHolderServiceTest {
    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private AccountHolder accountHolder;
    private LoginAccount loginAccount;
    private Address address;
    private Checking checking;
    private Saving saving;
    private CreditCard creditCard;
    private StudentChecking studentChecking;
    private Transference transference;
    private List<Account> accountsHolderList = new ArrayList<Account>();
    private List<LocalDateTime> listOfTransactions = new ArrayList<LocalDateTime>();

    @BeforeEach
    void setUp() {
        address = new Address("Portugal", "Faro", "Rua Nova", "9800");

        listOfTransactions.add(LocalDateTime.of(2020, 05, 05, 20, 00, 02));

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checking.setTransactionsMade(listOfTransactions);
        checkingRepository.save(checking);

        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);

        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        saving.setTransactionsMade(listOfTransactions);
        savingRepository.save(saving);

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentChecking.setTransactionsMade(listOfTransactions);
        studentCheckingRepository.save(studentChecking);

        accountHolder = new AccountHolder("Ana Santos", "pass", LocalDate.of(1995, 8, 19), address);
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

        transference = new Transference();
        loginAccount = new LoginAccount();
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        checkingRepository.deleteAll();
        savingRepository.deleteAll();
        creditCardRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        transference = null;
        loginAccount = null;
        address = null;
    }

    @Test
    void findAll() {
        assertEquals(1, accountHolderService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(accountHolder, accountHolderService.findById(5));
    }

    @Test
    void createAccountHolder() {
        AccountHolder accountHolder1 = new AccountHolder("Mary Santos", "pass", LocalDate.of(1989, 8, 19), address);
        accountHolderService.createAccountHolder(accountHolder1);
        assertEquals(2, accountHolderRepository.findAll().size());
    }

    @Test
    void checkAccountBalanceSavings() {
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.checkAccountBalance(saving.getAccountId(), accountHolder.getUserId());});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());

        accountHolderService.loginAccountHolder(loginAccount);
        assertEquals(saving.getBalance().getAmount() , accountHolderService.checkAccountBalance(saving.getAccountId(), accountHolder.getUserId()));
    }

    @Test
    void checkAccountBalanceCreditCard() {
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.checkAccountBalance(creditCard.getAccountId(), accountHolder.getUserId());});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());

        accountHolderService.loginAccountHolder(loginAccount);
        assertEquals(creditCard.getBalance().getAmount() , accountHolderService.checkAccountBalance(creditCard.getAccountId(), accountHolder.getUserId()));
    }

    @Test
    void checkAccountBalanceChecking() {
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.checkAccountBalance(checking.getAccountId(), accountHolder.getUserId());});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());

        accountHolderService.loginAccountHolder(loginAccount);
        assertEquals(checking.getBalance().getAmount() , accountHolderService.checkAccountBalance(checking.getAccountId(), accountHolder.getUserId()));
    }

    @Test
    void checkAccountBalanceStudentChecking() {
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.checkAccountBalance(studentChecking.getAccountId(), accountHolder.getUserId());});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());

        accountHolderService.loginAccountHolder(loginAccount);
        assertEquals(studentChecking.getBalance().getAmount() , accountHolderService.checkAccountBalance(studentChecking.getAccountId(), accountHolder.getUserId()));
    }

    @Test
    void transferAmountNotCorrectSenderName() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName("Sarah Chicago");
        transference.setSenderAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(1).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});
    }

    @Test
    void transferAmountNotCorrectReceiverName() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setReceiverName("Sarah Chicago");
        transference.setReceiverAccountId(accountHolder.getAccounts().get(1).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});
    }

    @Test
    void transferAmountSenderChecking() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(1).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);

        assertEquals(checking.getBalance(), accountHolder.getAccounts().get(0).getBalance());
    }

    @Test
    void transferAmountSenderCreditCard() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(1).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);

        assertEquals(creditCard.getBalance(), accountHolder.getAccounts().get(1).getBalance());
    }

    @Test
    void transferAmountSenderSaving() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(2).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);

        assertEquals(saving.getBalance(), accountHolder.getAccounts().get(2).getBalance());
    }

    @Test
    void transferAmountSenderStudentChecking() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(3).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.transferAmount(transference);});

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);

        assertEquals(studentChecking.getBalance(), accountHolder.getAccounts().get(3).getBalance());
    }

    @Test
    void fraudDetectionSavings() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(2).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.fraudDetectionSavings(saving);});

    }



    @Test
    void fraudDetectionChecking() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(2).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.fraudDetectionChecking(checking);});

    }

    @Test
    void fraudDetectionStudentChecking() {
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(3).getAccountId());
        transference.setReceiverName(accountHolder.getName());
        transference.setReceiverAccountId(accountHolder.getAccounts().get(2).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);

        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);

        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.fraudDetectionStudentChecking(studentChecking);});
    }

    @Test
    void loginAccountHolderPasswordIncorrect() {
        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword("hola");
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.loginAccountHolder(loginAccount);});
    }

    @Test
    void loginAccountHolder() {
        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolderService.loginAccountHolder(loginAccount);
        accountHolder.login();
        accountHolderRepository.save(accountHolder);
        assertEquals(true, accountHolder.isLogged());
    }

    @Test
    void logOutAccountHolderPasswordIncorrect() {
        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword("hola");
        assertThrows(DataNotFoundException.class, () -> {
            accountHolderService.logOutAccountHolder(loginAccount);});
    }

    @Test
    void logOutAccountHolder() {
        loginAccount.setId(accountHolder.getUserId());
        loginAccount.setPassword(accountHolder.getPassword());
        accountHolder.login();
        accountHolderRepository.save(accountHolder);
        accountHolderService.logOutAccountHolder(loginAccount);
        accountHolder.logOut();
        assertEquals(false, accountHolder.isLogged());
    }

}