package com.ironhack.MidtermProject.model.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();

        address = new Address("Portugal", "Faro", "Rua Nova", "9800");
    }

    @AfterEach
    void tearDown() {
        address = null;
    }

    @Test
    void getCountry() {
        address.setCountry("Portugal");

        assertEquals("Portugal", address.getCountry());
    }

    @Test
    void getCity() {
        address.setCity("Faro");

        assertEquals("Faro", address.getCity());
    }

    @Test
    void getStreet() {
        address.setStreet("Rua Nova");

        assertEquals("Rua Nova", address.getStreet());
    }

    @Test
    void getPostalCode() {
        address.setPostalCode("9800");

        assertEquals("9800", address.getPostalCode());
    }
}