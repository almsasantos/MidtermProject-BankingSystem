package com.ironhack.MidtermProject.controller.interfaces.users;

import com.ironhack.MidtermProject.model.entities.users.Users;

import java.util.List;

public interface UsersControllerInterface {
    /**
     * Find all Users created.
     * @return a list of users.
     */
    public List<Users> findAll();

    /**
     * Find User by id.
     * @param id receives an integer id of Users.
     * @return a Users corresponding to that id.
     */
    public Users findById(Integer id);
}
