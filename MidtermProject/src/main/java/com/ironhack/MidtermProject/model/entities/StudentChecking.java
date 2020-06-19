package com.ironhack.MidtermProject.model.entities;

import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account{
    public StudentChecking() {
    }

    public StudentChecking(Long accountId, BigDecimal balance, String secretKey, AccountHolders primaryOwner, BigDecimal penaltyFee, Status status) {
        super(accountId, balance, secretKey, primaryOwner, penaltyFee, status);
    }
}
