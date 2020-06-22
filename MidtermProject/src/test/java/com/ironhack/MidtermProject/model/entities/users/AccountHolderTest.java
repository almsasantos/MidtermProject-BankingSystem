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
        accountHolder.setAccounts(accountList);
        accountHolder.setUserId(1);
        accountHolder.setName("Ana");
        accountHolder.setPassword("pass");
        accountHolder.setDateOfBirth(LocalDate.of(1995, 8, 19));
        accountHolder.setMailingAddress("alms@gmail.com");
        accountHolder.setPrimaryAddress(address);
        accountHolder.setSecondaryAccounts(accountList);
    }

    @AfterEach
    void tearDown() {
        address = null;
        accountHolder = null;
    }

    @Test
    void getDateOfBirth() {
        LocalDate birthday = LocalDate.of(1995, 8, 19);
        assertEquals(birthday, accountHolder.getDateOfBirth());
    }

    @Test
    void getPrimaryAddress() {
        assertEquals(1, accountHolder.getUserId());
    }

    @Test
    void getMailingAddress() {
        assertEquals("alms@gmail.com", accountHolder.getMailingAddress());
    }


    @Test
    void getAccounts() {
        assertEquals(0, accountHolder.getAccounts().size());
    }

    @Test
    void getSecondaryAccounts() {
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
        assertEquals("pass", accountHolder.getPassword());
    }

}