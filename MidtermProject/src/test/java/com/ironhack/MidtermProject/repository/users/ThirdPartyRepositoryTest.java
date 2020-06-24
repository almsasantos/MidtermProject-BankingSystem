package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ThirdPartyRepositoryTest {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private ThirdParty thirdParty;

    @BeforeEach
    void setUp() {
        thirdParty = new ThirdParty();
        thirdParty.setName("Ana Santos");
        thirdParty.setPassword("pass");
        thirdPartyRepository.save(thirdParty);
    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    void findByName() {
        assertEquals(1, thirdPartyRepository.findByName("Ana Santos").size());
    }
}