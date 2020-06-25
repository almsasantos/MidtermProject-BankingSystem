package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.users.Users;
import com.ironhack.MidtermProject.repository.users.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UsersRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    public List<Users> findAll(){
        LOGGER.info("Get all users");
        return userRepository.findAll();
    }

    public Users findById(Integer id){
        LOGGER.info("Get user by " + id);
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User id not found"));
    }
}
