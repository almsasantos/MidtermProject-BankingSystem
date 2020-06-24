package com.ironhack.MidtermProject.model.entities.accounts;

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

class CreditCardTest {
    private CreditCard creditCard = new CreditCard();
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

        creditCard = new CreditCard();
        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCard.setAccountId(1);
        creditCard.setBalance(new Money(new BigDecimal("900")));
        creditCard.setPenaltyFee(new BigDecimal("40"));
        creditCard.setPrimaryOwner(accountHolder);

        accountList.add(creditCard);
    }

    @AfterEach
    void tearDown() {
        creditCard = null;
        address = null;
        accountHolder = null;
        accountList = null;
    }

    @Test
    void getCreditLimit() {
        creditCard.setCreditLimit(new BigDecimal("100"));

        assertEquals(new BigDecimal("100"), creditCard.getCreditLimit());
    }

    @Test
    void getInterestRate() {
        creditCard.setInterestRate(new BigDecimal("0.2"));

        assertEquals(new BigDecimal("0.2"), creditCard.getInterestRate());
    }

    @Test
    void getDate() {
        creditCard.setDate(LocalDate.now());

        assertEquals(LocalDate.now(), creditCard.getDate());
    }

    @Test
    void getLastInterestDate() {
        creditCard.setLastInterestDate(LocalDate.now());

        assertEquals(LocalDate.now(), creditCard.getLastInterestDate());
    }
}