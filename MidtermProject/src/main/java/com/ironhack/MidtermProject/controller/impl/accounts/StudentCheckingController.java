package com.ironhack.MidtermProject.controller.impl.accounts;

import com.ironhack.MidtermProject.controller.interfaces.accounts.StudentCheckingControllerInterface;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;
import com.ironhack.MidtermProject.service.accounts.StudentCheckingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Student Checking Account Controller")
@RestController
@RequestMapping("/")
public class StudentCheckingController implements StudentCheckingControllerInterface {
    @Autowired
    private StudentCheckingService studentCheckingService;

    /**
     * Find all StudentChecking accounts.
     * @return a list of student checking accounts.
     */
    @GetMapping("/student-checking-accounts")
    @ApiOperation(value="Find All Student Checking Accounts",
            notes = "Lists all student checking accounts created",
            response = StudentChecking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> findAll(){
        return studentCheckingService.findAll();
    }

    /**
     * Find StudentChecking accounts by Id.
     * @param id receives an integer id of account.
     * @return a student checking account corresponding to that id.
     */
    @GetMapping("/student-checking-accounts/{id}")
    @ApiOperation(value="Find Student Checking Accounts by Id",
            response = StudentChecking.class)
    @ResponseStatus(HttpStatus.OK)
    public StudentChecking findById(@PathVariable("id") Integer id){
        return studentCheckingService.findById(id);
    }

    /**
     * Find StudentChecking accounts based on the status.
     * @param status receives an enum of Status.
     * @return a list of student checking accounts.
     */
    @GetMapping("/student-checking-accounts/status")
    @ApiOperation(value="Find Student Checking Accounts by Status",
            notes = "Lists all student checking accounts by status",
            response = StudentChecking.class)
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> findByStatus(@RequestParam("status") Status status){
        return studentCheckingService.findByStatus(status);
    }
}
