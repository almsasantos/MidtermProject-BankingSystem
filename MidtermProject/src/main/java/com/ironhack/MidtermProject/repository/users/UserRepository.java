package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
