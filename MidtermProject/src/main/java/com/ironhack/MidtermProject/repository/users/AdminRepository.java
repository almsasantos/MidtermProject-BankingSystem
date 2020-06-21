package com.ironhack.MidtermProject.repository.users;

import com.ironhack.MidtermProject.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query(value = "SELECT account_id, balance FROM account WHERE account_id = :account_id", nativeQuery = true)
    public List<Object[]> checkAccountBalance(@Param("account_id") Integer accountId);
}
