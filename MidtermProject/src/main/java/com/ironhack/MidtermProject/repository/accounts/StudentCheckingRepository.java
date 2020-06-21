package com.ironhack.MidtermProject.repository.accounts;

import com.ironhack.MidtermProject.model.entities.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Integer> {
}
