package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Saving, Integer> {
}
