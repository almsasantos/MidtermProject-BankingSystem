package com.ironhack.MidtermProject.handler;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GlobalHandlerTest {

    GlobalHandler globalHandler = new GlobalHandler();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleOpportunityIdNotFoundException() throws IOException {
        assertThrows(Exception.class, () ->  globalHandler.handleDataIdNotFoundException(new DataNotFoundException("Not found."), null));
    }
}