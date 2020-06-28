package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.controller.interfaces.users.UsersControllerInterface;
import com.ironhack.MidtermProject.model.entities.users.Users;
import com.ironhack.MidtermProject.service.users.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User Controller")
@RestController
@RequestMapping("/")
public class UsersController implements UsersControllerInterface {
    @Autowired
    private UsersService userService;

    /**
     * Find all Users created.
     * @return a list of users.
     */
    @GetMapping("/users")
    @ApiOperation(value="Find All Users",
            notes = "Lists all users created",
            response = Users.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Users> findAll(){
        return userService.findAll();
    }

    /**
     * Find User by id.
     * @param id receives an integer id of Users.
     * @return a Users corresponding to that id.
     */
    @GetMapping("/users/{id}")
    @ApiOperation(value="Find User by Id",
            response = Users.class)
    @ResponseStatus(HttpStatus.OK)
    public Users findById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }

}
