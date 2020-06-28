package com.ironhack.MidtermProject.repository.security;

import com.ironhack.MidtermProject.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
