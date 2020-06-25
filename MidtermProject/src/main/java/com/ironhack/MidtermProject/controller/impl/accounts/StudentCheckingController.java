package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.service.accounts.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentCheckingController {
    @Autowired
    private StudentCheckingService studentCheckingService;

    @GetMapping("/student-checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> findAll(){
        return studentCheckingService.findAll();
    }

    @GetMapping("/student-checking-accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentChecking findById(@PathVariable("id") Integer id){
        return studentCheckingService.findById(id);
    }

    @GetMapping("/student-checking-accounts/status")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> findByStatus(@RequestParam("status") Status status){
        return studentCheckingService.findByStatus(status);
    }
}
