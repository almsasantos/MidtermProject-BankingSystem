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

        saving.setAccountId(1);
        saving.setBalance(new Money(new BigDecimal("900")));
        saving.setSecretKey("000000");
        saving.setPenaltyFee(new BigDecimal("40"));
        saving.setStatus(Status.ACTIVE);
        saving.setPrimaryOwner(accountHolder);
        saving.setMinimumBalance(new BigDecimal("1000"));
        saving.setInterestRate(new BigDecimal("0.025"));
        saving.setDate(LocalDate.now());

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
        assertEquals(new BigDecimal("0.025"), saving.getInterestRate());
    }

    @Test
    void getMinimumBalance() {
        assertEquals(new BigDecimal("1000"), saving.getMinimumBalance());
    }

    @Test
    void getDate() {
        assertEquals(LocalDate.now(), saving.getDate());
    }

    @Test
    void getLastInterestDate() {
        assertEquals(null, saving.getLastInterestDate());
    }

    @Test
    void getAccountId() {
        assertEquals(1, saving.getAccountId());
    }

    @Test
    void getBalance() {
        assertEquals(new Money(new BigDecimal("900")), saving.getBalance());
    }

    @Test
    void getSecretKey() {
        assertEquals("000000", saving.getSecretKey());
    }

    @Test
    void getPenaltyFee() {
        assertEquals(new BigDecimal("40"), saving.getPenaltyFee());
    }

    @Test
    void getStatus() {
        assertEquals(Status.ACTIVE, saving.getStatus());
    }

    @Test
    void getAccountType() {
        assertEquals(AccountType.SAVINGS, saving.getAccountType());
    }

    @Test
    void getPrimaryOwner() {
        assertEquals(accountHolder, saving.getPrimaryOwner());
    }

    @Test
    void getSecondaryOwner() {
        assertEquals(null, saving.getSecondaryOwner());
    }

    @Test
    void testToString() {
        String savingString = saving.toString();
        assertEquals(savingString, saving.toString());
    }
}