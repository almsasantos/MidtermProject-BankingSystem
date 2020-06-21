package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.User;
import com.ironhack.MidtermProject.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User id not found"));
    }
}
