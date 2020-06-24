package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.Status;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private AccountHolder accountHolder1;
    private Address address;
    private Checking checking;
    private Saving saving;
    private Saving saving2;
    private CreditCard creditCard;
    private StudentChecking studentChecking;
    private Transference transference;
    private List<Account> accountsHolderList = new ArrayList<Account>();
    private List<Account> accountsHolderList1 = new ArrayList<Account>();

    @BeforeEach
    void setUp() {
        address = new Address("Portugal", "Faro", "Rua Nova", "9800");

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checkingRepository.save(checking);

        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);

        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving);

        saving2 = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving2);

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentCheckingRepository.save(studentChecking);

        accountHolder = new AccountHolder("Ana Santos", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");
        accountsHolderList.add(saving);
        accountHolder.setAccounts(accountsHolderList);
        accountHolderRepository.save(accountHolder);

        accountHolder1 = new AccountHolder("Ana Martins", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");
        accountsHolderList1.add(saving2);
        accountHolder1.setAccounts(accountsHolderList1);
        accountHolderRepository.save(accountHolder1);


        saving.setPrimaryOwner(accountHolder);
        savingRepository.save(saving);
        saving2.setPrimaryOwner(accountHolder1);
        savingRepository.save(saving2);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        checkingRepository.deleteAll();
        savingRepository.deleteAll();
        creditCardRepository.deleteAll();
        studentCheckingRepository.deleteAll();
    }

    @Test
    void findAll() {
        assertEquals(1, accountHolderService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(accountHolder, accountHolderService.findById(accountHolder.getUserId()));
    }

    @Test
    void createAccountHolder() {
    }

    @Test
    void checkAccountBalance() {
    }

    @Test
    void fraudDetectionSavings() {
        transference = new Transference();
        transference.setUserId(accountHolder.getUserId());
        transference.setSenderName(accountHolder.getName());
        transference.setSenderAccountId(accountHolder.getAccounts().get(0).getAccountId());
        transference.setReceiverName(accountHolder1.getName());
        transference.setReceiverAccountId(accountHolder1.getAccounts().get(0).getAccountId());
        transference.setAmountToTransfer(new BigDecimal("10"));

        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);
        accountHolderService.transferAmount(transference);

        assertEquals(new BigDecimal("890"), accountHolder.getAccounts().get(0).getBalance());
    }

    @Test
    void fraudDetectionChecking() {
    }

    @Test
    void fraudDetectionStudentChecking() {
    }

    @Test
    void transferAmount() {
    }
}