package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
