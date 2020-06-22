package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
}
