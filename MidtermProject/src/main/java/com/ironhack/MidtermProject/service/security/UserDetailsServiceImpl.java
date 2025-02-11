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

    /**
     * This method found a match between login user and user from data base
     * @param username a String value
     * @return a userDetails element
     * @throws UsernameNotFoundException If there isn't a match
     */
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
