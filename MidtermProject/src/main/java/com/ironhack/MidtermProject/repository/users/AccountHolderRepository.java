package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    @Query(value = "SELECT a.account_id, a.balance FROM account a JOIN account_holders h ON a.owner_id = h.user_id WHERE a.account_id = :account_id", nativeQuery = true)
    public List<Object[]> checkAccountBalance(@Param(value = "account_id") Integer accountId);
}
