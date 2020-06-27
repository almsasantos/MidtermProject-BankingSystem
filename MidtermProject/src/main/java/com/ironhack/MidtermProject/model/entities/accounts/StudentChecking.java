package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentChecking entity inherit from Account
 */
@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
    /**
     * StudentChecking secretKey, must have 6 integer digits
     */
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    /**
     * StudentChecking status of account
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    /**
     * StudentChecking list of transactionsMade
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> transactionsMade;

    /**
     * Void Constructor
     */
    public StudentChecking() {
        this.transactionsMade = new ArrayList<LocalDateTime>();
        this.accountType = AccountType.STUDENT_CHECKING;
    }

    /**
     * Constructor
     * @param balance Receives the balance for StudentChecking account
     * @param secretKey Receives the secretKey for StudentChecking account
     * @param status Receives the status for StudentChecking account
     */
    public StudentChecking(Money balance, String secretKey, Status status) {
        super(balance);
        this.secretKey = secretKey;
        this.status = status;
        this.accountType = AccountType.STUDENT_CHECKING;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    /**
     * Getter of Student Checking's secretKey
     * @return Student Checking's secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter of Student Checking's secretKey
     * @param secretKey Receives Student Checking's secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter of Student Checking's status
     * @return Student Checking's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter of Student Checking's status
     * @param status Receives Student Checking's status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of Student Checking's transactionsMade
     * @return List of Student Checking's transactionsMade
     */
    public List<LocalDateTime> getTransactionsMade() {
        return transactionsMade;
    }

    /**
     * Setter of Student Checking's transactionsMade
     * @param transactionsMade Receives a list of Student Checking's transactionsMade
     */
    public void setTransactionsMade(List<LocalDateTime> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }
}
