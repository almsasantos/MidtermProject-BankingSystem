package com.ironhack.MidtermProject.service.security;

import com.ironhack.MidtermProject.model.security.User;
import com.ironhack.MidtermProject.repository.security.UserRepository;
import com.ironhack.MidtermProject.security.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            return new CustomSecurityUser(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username/password combination.");
        }

    }
}
