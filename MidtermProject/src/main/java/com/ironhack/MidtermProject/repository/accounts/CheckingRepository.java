package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {
}
