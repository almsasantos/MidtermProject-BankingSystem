package com.ironhack.MidtermProject.controller.interfaces.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.entities.accounts.StudentChecking;

import java.util.List;

public interface StudentCheckingControllerInterface {

    /**
     * Find all StudentChecking accounts.
     * @return a list of student checking accounts.
     */
    public List<StudentChecking> findAll();

    /**
     * Find StudentChecking accounts by Id.
     * @param id receives an integer id of account.
     * @return a student checking account corresponding to that id.
     */
    public StudentChecking findById(Integer id);

    /**
     * Find StudentChecking accounts based on the status.
     * @param status receives an enum of Status.
     * @return a list of student checking accounts.
     */
    public List<StudentChecking> findByStatus(Status status);
}
