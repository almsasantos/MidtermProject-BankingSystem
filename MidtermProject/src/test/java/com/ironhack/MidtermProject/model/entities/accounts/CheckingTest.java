package com.ironhack.MidtermProject.model.entities.accounts;

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

class CheckingTest {
    private Checking checking = new Checking();
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

        checking.setAccountId(1);
        checking.setBalance(new Money(new BigDecimal("900")));
        checking.setSecretKey("000000");
        checking.setPenaltyFee(new BigDecimal("40"));
        checking.setStatus(Status.ACTIVE);
        checking.setPrimaryOwner(accountHolder);
        checking.setMinimumBalance(new BigDecimal("250"));
        checking.setMonthlyMaintenanceFee(new BigDecimal("12"));

        accountList.add(checking);
    }

    @AfterEach
    void tearDown() {
        checking = null;
        address = null;
        accountHolder = null;
        accountList = null;
    }

    @Test
    void getMinimumBalance() {
        assertEquals(new BigDecimal("250"), checking.getMinimumBalance());
    }

    @Test
    void getMonthlyMaintenanceFee() {
        assertEquals(new BigDecimal("12"), checking.getMonthlyMaintenanceFee());
    }

}