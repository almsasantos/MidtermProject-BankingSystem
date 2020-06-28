package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CreditCardRepositoryTest {
    @Autowired
    private CreditCardRepository creditCardRepository;

    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    void tearDown() {
        creditCardRepository.deleteAll();
    }

    @Test
    void findByCreditLimit() {
        assertEquals(1, creditCardRepository.findByCreditLimit(new BigDecimal("100")).size());
    }

    @Test
    void findByInterestRate() {
        assertEquals(1, creditCardRepository.findByInterestRate(new BigDecimal("0.2")).size());
    }

    @Test
    void findByDate() {
        assertEquals(1, creditCardRepository.findByDate(LocalDate.now()).size());
    }
}