package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AccountHolderRepositoryTest {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private AccountHolder accountHolder;
    private Admin admin;
    private Address address;
    private Checking checking;
    private Saving saving;
    private CreditCard creditCard;
    private StudentChecking studentChecking;

    @BeforeEach
    void setUp() {
        address = new Address("Portugal", "Faro", "Rua Nova", "9800");
        accountHolder = new AccountHolder("Ana Santos", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");
        accountHolderRepository.save(accountHolder);

        admin = new Admin("Ana Santos", "pass");
        adminRepository.save(admin);

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checkingRepository.save(checking);

        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving);

        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentCheckingRepository.save(studentChecking);
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        checkingRepository.deleteAll();
        savingRepository.deleteAll();
        creditCardRepository.deleteAll();
        studentCheckingRepository.deleteAll();
    }

    @Test
    void findByName() {
        assertEquals(1, accountHolderRepository.findByName("Ana Santos").size());
    }

    @Test
    void findByDateOfBirth() {
        assertEquals(1, accountHolderRepository.findByDateOfBirth(LocalDate.of(1995, 8, 19)).size());
    }

    @Test
    void findByMailingAddress() {
        assertEquals(accountHolder, accountHolderRepository.findByMailingAddress("alms@gmail.com"));
    }

    @Test
    void checkSavingsBalance() {
        assertEquals(new BigDecimal("900").setScale(2), accountHolderRepository.checkSavingsBalance(saving.getAccountId()));
    }

    @Test
    void checkCheckingBalance() {
        assertEquals(new BigDecimal("900").setScale(2), accountHolderRepository.checkCheckingBalance(checking.getAccountId()));
    }

    @Test
    void checkStudentCheckingBalance() {
        assertEquals(new BigDecimal("100").setScale(2), accountHolderRepository.checkStudentCheckingBalance(studentChecking.getAccountId()));
    }

    @Test
    void checkCreditCardBalance() {
        assertEquals(new BigDecimal("900").setScale(2), accountHolderRepository.checkCreditCardBalance(creditCard.getAccountId()));
    }
}