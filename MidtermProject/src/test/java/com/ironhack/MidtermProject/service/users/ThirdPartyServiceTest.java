package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ThirdPartyServiceTest {
    @MockBean
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private ThirdParty thirdParty;
    private HashMap<String, String> thirdPartyDetails = new HashMap<String, String>();

    private Checking checking;
    private Checking checking2;
    private CreditCard creditCard;
    private CreditCard creditCard2;
    private Saving saving;
    private Saving saving2;
    private StudentChecking studentChecking;
    private StudentChecking studentChecking2;
    private ThirdPartyTransaction thirdPartyTransaction;

    @BeforeEach
    void setUp() {
        thirdParty = new ThirdParty("Ana Santos", "pass");
        thirdPartyDetails.put("1234", "Toyota");
        thirdParty.setAccountDetails(thirdPartyDetails);
        thirdParty.setUserId(1);

        checking = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checkingRepository.save(checking);

        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);

        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        savingRepository.save(saving);

        studentChecking = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentCheckingRepository.save(studentChecking);

        thirdPartyTransaction = new ThirdPartyTransaction();

        List<ThirdParty> thirdPartyList = Arrays.asList(thirdParty);
        when(thirdPartyRepository.findAll()).thenReturn(thirdPartyList);
        when(thirdPartyRepository.findById(thirdParty.getUserId())).thenReturn(Optional.of(thirdParty));
    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        savingRepository.deleteAll();
        creditCardRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        thirdParty = null;
        thirdPartyTransaction = null;
    }

    @Test
    void findAll() {
        assertEquals(1, thirdPartyService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(thirdParty, thirdPartyService.findById(thirdParty.getUserId()));
    }

    @Test
    void debitTransactionCheckingAccount() {
        thirdPartyTransaction.setAccountId(checking.getAccountId());
        thirdPartyTransaction.setSecretKey(checking.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));

        thirdPartyService.debitTransaction("1234", thirdPartyTransaction);

        checking2 = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checking2.setBalance(new Money(checking2.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));

        assertEquals(checking2.getBalance(), checking.getBalance());
    }

    @Test
    void debitTransactionCreditCard() {
        thirdPartyTransaction.setAccountId(creditCard.getAccountId());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.debitTransaction("1234", thirdPartyTransaction);

        creditCard2 = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCard2.setBalance(new Money(creditCard2.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
        creditCardRepository.save(creditCard2);

        assertEquals(creditCard2.getBalance(), creditCard.getBalance());
    }

    @Test
    void debitTransactionSaving() {
        thirdPartyTransaction.setAccountId(saving.getAccountId());
        thirdPartyTransaction.setSecretKey(saving.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.debitTransaction("1234", thirdPartyTransaction);

        saving2 = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        saving2.setBalance(new Money(saving2.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
        savingRepository.save(saving2);

        assertEquals(saving2.getBalance(), saving.getBalance());
    }

    @Test
    void debitTransactionStudentChecking() {
        thirdPartyTransaction.setAccountId(studentChecking.getAccountId());
        thirdPartyTransaction.setSecretKey(studentChecking.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.debitTransaction("1234", thirdPartyTransaction);

        studentChecking2 = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentChecking2.setBalance(new Money(studentChecking2.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
        studentCheckingRepository.save(studentChecking2);

        assertEquals(studentChecking2.getBalance(), studentChecking.getBalance());
    }

    @Test
    void creditTransactionCheckingAccount() {
        thirdPartyTransaction.setAccountId(checking.getAccountId());
        thirdPartyTransaction.setSecretKey(checking.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));

        thirdPartyService.creditTransaction("1234", thirdPartyTransaction);

        checking2 = new Checking(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("250"), new BigDecimal("12"));
        checking2.setBalance(new Money(checking2.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
        checkingRepository.save(checking2);

        assertEquals(checking2.getBalance(), checking.getBalance());
    }

    @Test
    void creditTransactionCreditCard() {
        thirdPartyTransaction.setAccountId(creditCard.getAccountId());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.creditTransaction("1234", thirdPartyTransaction);

        creditCard2 = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCard2.setBalance(new Money(creditCard2.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
        creditCardRepository.save(creditCard2);

        assertEquals(creditCard2.getBalance(), creditCard.getBalance());
    }

    @Test
    void creditTransactionSaving() {
        thirdPartyTransaction.setAccountId(saving.getAccountId());
        thirdPartyTransaction.setSecretKey(saving.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.creditTransaction("1234", thirdPartyTransaction);

        saving2 = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        saving2.setBalance(new Money(saving2.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
        savingRepository.save(saving2);

        assertEquals(saving2.getBalance(), saving.getBalance());
    }

    @Test
    void creditTransactionStudentChecking() {
        thirdPartyTransaction.setAccountId(studentChecking.getAccountId());
        thirdPartyTransaction.setSecretKey(studentChecking.getSecretKey());
        thirdPartyTransaction.setAmount(new BigDecimal("10"));
        thirdPartyService.creditTransaction("1234", thirdPartyTransaction);

        studentChecking2 = new StudentChecking(new Money(new BigDecimal("100")), "000000", Status.ACTIVE);
        studentChecking2.setBalance(new Money(studentChecking2.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
        studentCheckingRepository.save(studentChecking2);

        assertEquals(studentChecking2.getBalance(), studentChecking.getBalance());
    }

    @Test
    void checkHashedKeyExists() {
        assertThrows(DataNotFoundException.class, () -> {
            thirdPartyService.checkHashedKeyExists("9090");});
    }
}