package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
