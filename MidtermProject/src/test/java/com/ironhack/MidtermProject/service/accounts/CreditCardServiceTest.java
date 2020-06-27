package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.CreditCard;
import com.ironhack.MidtermProject.repository.accounts.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreditCardServiceTest {
    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    private CreditCard creditCard;
    private List<CreditCard> creditCardList = new ArrayList<CreditCard>();

    @BeforeEach
    void setUp() {
        creditCard = new CreditCard(new Money(new BigDecimal("900")), new BigDecimal("100"), new BigDecimal("0.2"));

        creditCardList.add(creditCard);

        when(creditCardRepository.findAll()).thenReturn(creditCardList);
        when(creditCardRepository.findById(creditCard.getAccountId())).thenReturn(Optional.of(creditCard));
        when(creditCardRepository.findByInterestRate(creditCard.getInterestRate())).thenReturn(creditCardList);
        when(creditCardRepository.findByDate(creditCard.getDate())).thenReturn(creditCardList);
        when(creditCardRepository.findByCreditLimit(creditCard.getCreditLimit())).thenReturn(creditCardList);
    }

    @Test
    void findAll() {
        assertEquals(creditCardList, creditCardService.findAll());
    }

    @Test
    void findById() {
        assertEquals(creditCard, creditCardService.findById(creditCard.getAccountId()));
    }

    @Test
    void findByCreditLimit() {
        assertEquals(creditCardList, creditCardService.findByCreditLimit(creditCard.getCreditLimit()));
    }

    @Test
    void findByInterestRate() {
        assertEquals(creditCardList, creditCardService.findByInterestRate(creditCard.getInterestRate()));
    }

    @Test
    void findByDate() {
        assertEquals(creditCardList, creditCardService.findByDate(creditCard.getDate()));
    }
}