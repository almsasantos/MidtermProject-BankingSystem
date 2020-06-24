package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.Users;
import com.ironhack.MidtermProject.repository.users.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UsersRepository userRepository;

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin("Ana Santos", "pass");

        List<Users> users = Arrays.asList(admin);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(admin.getUserId())).thenReturn(Optional.of(admin));
    }

    @Test
    void findAll() {
        assertEquals(1, userService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(admin, userService.findById(admin.getUserId()));
    }
}