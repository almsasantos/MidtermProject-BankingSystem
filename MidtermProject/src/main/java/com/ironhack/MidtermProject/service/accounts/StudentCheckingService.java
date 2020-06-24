package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.repository.accounts.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCheckingService {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    public List<StudentChecking> findAll(){
        return studentCheckingRepository.findAll();
    }

    public StudentChecking findById(Integer id){
        return studentCheckingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Student checking id not found"));
    }

    public List<StudentChecking> findBySecretKey(String secretKey){
        return studentCheckingRepository.findBySecretKey(secretKey);
    }

    public List<StudentChecking> findByStatus(Status status){
        return studentCheckingRepository.findByStatus(status);
    }
}
