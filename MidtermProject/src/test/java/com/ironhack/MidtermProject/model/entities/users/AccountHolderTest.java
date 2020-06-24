package com.ironhack.MidtermProject.model.entities.users;

import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountHolderTest {
    private AccountHolder accountHolder;
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
        accountHolder = new AccountHolder("Ana", "pass", LocalDate.of(1995, 8, 19), address);
        accountHolder = new AccountHolder("Ana", "pass", LocalDate.of(1995, 8, 19), address, "alms@gmail.com");
    }

    @AfterEach
    void tearDown() {
        address = null;
        accountHolder = null;
    }

    @Test
    void getUserId() {
        accountHolder.setUserId(1);

        assertEquals(1, accountHolder.getUserId());
    }

    @Test
    void getDateOfBirth() {
        accountHolder.setDateOfBirth(LocalDate.of(1995, 8, 19));

        assertEquals(LocalDate.of(1995, 8, 19), accountHolder.getDateOfBirth());
    }

    @Test
    void getPrimaryAddress() {
        accountHolder.setPrimaryAddress(address);

        assertEquals(address, accountHolder.getPrimaryAddress());
    }

    @Test
    void getMailingAddress() {
        accountHolder.setMailingAddress("alms@gmail.com");

        assertEquals("alms@gmail.com", accountHolder.getMailingAddress());
    }


    @Test
    void getAccounts() {
        accountHolder.setAccounts(accountList);

        assertEquals(0, accountHolder.getAccounts().size());
    }

    @Test
    void getSecondaryAccounts() {
        accountHolder.setSecondaryAccounts(accountList);

        assertEquals(0, accountHolder.getSecondaryAccounts().size());
    }

    @Test
    void login() {
        accountHolder.login();

        assertEquals(true, accountHolder.isLogged());
    }

    @Test
    void logOut() {
        accountHolder.logOut();

        assertEquals(false, accountHolder.isLogged());
    }

    @Test
    void isLogged() {
        assertEquals(false, accountHolder.isLogged());
    }

    @Test
    void getPassword() {
        accountHolder.setPassword("pass");

        assertEquals("pass", accountHolder.getPassword());
    }

    @Test
    void getName() {
        accountHolder.setName("Ana");

        assertEquals("Ana", accountHolder.getName());
    }

    @Test
    void testToString() {
        String accountHolderString = accountHolder.toString();

        assertEquals(accountHolderString, accountHolder.toString());
    }

    @Test
    void testEquals() {
        AccountHolder accountHolderCopy = accountHolder;
        assertEquals(accountHolderCopy, accountHolder);
    }

    @Test
    void getHashCode(){
        assertEquals(accountHolder.hashCode(), accountHolder.hashCode());
    }

}