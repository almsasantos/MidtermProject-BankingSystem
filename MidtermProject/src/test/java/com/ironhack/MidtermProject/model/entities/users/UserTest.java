package com.ironhack.MidtermProject.model.entities.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserTest {
    public User user;

    @BeforeEach
    void setUp() {
        user.setUserId(1);
        user.setName("Ana");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserId() {
    }

    @Test
    void setUserId() {
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void login() {
    }

    @Test
    void logOut() {
    }

    @Test
    void isLogged() {
    }
}