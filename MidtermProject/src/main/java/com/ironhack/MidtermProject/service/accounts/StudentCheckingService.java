package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCheckingService {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private static final Logger LOGGER = LogManager.getLogger(StudentCheckingService.class);

    public List<StudentChecking> findAll(){
        LOGGER.info("Get all Student Checking accounts");
        return studentCheckingRepository.findAll();
    }

    public StudentChecking findById(Integer id){
        LOGGER.info("Get Student Checking account with id " + id);
        return studentCheckingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Student checking id not found"));
    }

    public List<StudentChecking> findByStatus(Status status){
        LOGGER.info("Get Student Checking account with status " + status);
        return studentCheckingRepository.findByStatus(status);
    }
}
