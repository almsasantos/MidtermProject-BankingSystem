package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.model.entities.StudentChecking;
import com.ironhack.MidtermProject.service.accounts.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentCheckingController {
    @Autowired
    private StudentCheckingService studentCheckingService;

    @GetMapping("/student_checking_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> findAll(){
        return studentCheckingService.findAll();
    }

    @GetMapping("/student_checking_accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentChecking findById(@PathVariable("id") Integer id){
        return studentCheckingService.findById(id);
    }
}
