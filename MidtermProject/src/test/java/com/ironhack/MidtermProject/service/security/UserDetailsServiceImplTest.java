package com.ironhack.MidtermProject.service.security;

import com.ironhack.MidtermProject.model.security.Role;
import com.ironhack.MidtermProject.model.security.User;
import com.ironhack.MidtermProject.repository.security.RoleRepository;
import com.ironhack.MidtermProject.repository.security.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    public User user1;
    public Role role1;

    @BeforeEach
    void setUp() {
        user1 = new User("User1", "bananas");
        userRepo.save(user1);
        role1 = new Role("admin");
        role1.setUser(user1);
        roleRepository.save(role1);
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void loadUserByUsername() {
        UserDetails user = userDetailsService.loadUserByUsername(user1.getUsername());
        assertNotNull(user);
    }

    @Test
    void loadUserByUsernameException() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("admin"));
    }
}