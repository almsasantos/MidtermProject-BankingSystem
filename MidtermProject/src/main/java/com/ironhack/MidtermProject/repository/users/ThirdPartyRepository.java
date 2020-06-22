package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Integer> {
}
