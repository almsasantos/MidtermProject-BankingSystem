package com.ironhack.MidtermProject.repository.security;


import com.ironhack.MidtermProject.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This method search user whose name attribute matches with param
     * @param username a String value
     * @return A user whose username attribute matches with username param
     */
    User findByUsername(String username);
}
