package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.model.entities.User;
import com.ironhack.MidtermProject.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }
}
