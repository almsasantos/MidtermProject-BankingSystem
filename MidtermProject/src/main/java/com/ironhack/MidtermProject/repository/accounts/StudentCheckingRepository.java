package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Integer> {
    public List<StudentChecking> findBySecretKey(String secretKey);

    public List<StudentChecking> findByStatus(Status status);

}
