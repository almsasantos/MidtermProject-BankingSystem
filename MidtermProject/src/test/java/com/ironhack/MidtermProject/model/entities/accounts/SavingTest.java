package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SavingTest {
    private Saving saving = new Saving();
    private AccountHolder accountHolder = new AccountHolder();
    List<Account> accountList = new ArrayList<Account>();
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setCountry("Spain");
        address.setCity("Madrid");
        address.setPostalCode("28033");
        address.setStreet("Calle Golfo de Salonica");

        accountHolder = new AccountHolder();
        accountHolder.setAccounts(accountList);
        accountHolder.setUserId(1);
        accountHolder.setName("Ana");
        accountHolder.setPassword("pass");
        accountHolder.setDateOfBirth(LocalDate.of(1995, 8, 19));
        accountHolder.setMailingAddress("alms@gmail.com");
        accountHolder.setPrimaryAddress(address);

        saving = new Saving();
        saving = new Saving(new Money(new BigDecimal("900")), "000000", Status.ACTIVE, new BigDecimal("0.025"), new BigDecimal("1000"));
        saving.setAccountId(1);

        accountList.add(saving);
    }

    @AfterEach
    void tearDown() {
        saving = null;
        address = null;
        accountHolder = null;
        accountList = null;
    }

    @Test
    void getInterestRate() {
        saving.setInterestRate(new BigDecimal("0.025"));

        assertEquals(new BigDecimal("0.025"), saving.getInterestRate());
    }

    @Test
    void getMinimumBalance() {
        saving.setMinimumBalance(new BigDecimal("1000"));

        assertEquals(new BigDecimal("1000"), saving.getMinimumBalance());
    }

    @Test
    void getDate() {
        saving.setDate(LocalDate.now());

        assertEquals(LocalDate.now(), saving.getDate());
    }

    @Test
    void getLastInterestDate() {
        saving.setLastInterestDate(null);
        assertEquals(null, saving.getLastInterestDate());
    }

    @Test
    void getAccountId() {
        assertEquals(1, saving.getAccountId());
    }

    @Test
    void getBalance() {
        saving.setBalance(new Money(new BigDecimal("900")));

        assertEquals(new Money(new BigDecimal("900")), saving.getBalance());
    }

    @Test
    void getSecretKey() {
        saving.setSecretKey("000000");

        assertEquals("000000", saving.getSecretKey());
    }

    @Test
    void getPenaltyFee() {
        saving.setPenaltyFee(new BigDecimal("40"));

        assertEquals(new BigDecimal("40"), saving.getPenaltyFee());
    }

    @Test
    void getStatus() {
        saving.setStatus(Status.ACTIVE);

        assertEquals(Status.ACTIVE, saving.getStatus());
    }

    @Test
    void getAccountType() {
        assertEquals(AccountType.SAVINGS, saving.getAccountType());
    }

    @Test
    void getPrimaryOwner() {
        saving.setPrimaryOwner(accountHolder);

        assertEquals(accountHolder, saving.getPrimaryOwner());
    }

    @Test
    void getSecondaryOwner() {
        assertEquals(null, saving.getSecondaryOwner());
    }

    @Test
    void getLastPenalty() {
        saving.setLastPenalty(0);

        assertEquals(0, saving.getLastPenalty());
    }

    @Test
    void getTransactionsMade() {
        List<LocalDateTime> transactionsMade = new ArrayList<LocalDateTime>();

        assertEquals(0, saving.getTransactionsMade().size());
    }

    @Test
    void testToString() {
        String savingString = saving.toString();

        assertEquals(savingString, saving.toString());
    }
}