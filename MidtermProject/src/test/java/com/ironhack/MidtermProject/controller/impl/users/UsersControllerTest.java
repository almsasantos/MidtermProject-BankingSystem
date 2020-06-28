package com.ironhack.MidtermProject.controller.impl.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.Users;
import com.ironhack.MidtermProject.service.users.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UsersControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UsersService userService;

    private MockMvc mockMvc;
    private Admin admin;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        admin = new Admin("Ana", "pass");

        List<Users> adminList = Arrays.asList(admin);
        when(userService.findAll()).thenReturn(adminList);
        when(userService.findById(1)).thenReturn(admin);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(admin.getUserId()))
                .andExpect(jsonPath("$[0].name").value(admin.getName()))
                .andExpect(jsonPath("$[0].password").value(admin.getPassword()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(admin.getUserId()))
                .andExpect(jsonPath("$.name").value(admin.getName()))
                .andExpect(jsonPath("$.password").value(admin.getPassword()));
    }
}