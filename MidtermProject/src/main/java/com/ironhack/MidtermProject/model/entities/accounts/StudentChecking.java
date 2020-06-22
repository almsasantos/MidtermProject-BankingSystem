package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
    public StudentChecking() {
        this.accountType = AccountType.STUDENT_CHECKING;
    }

    public StudentChecking(BigDecimal balance, String secretKey, Status status) {
        super(balance, secretKey, status);
        this.accountType = AccountType.STUDENT_CHECKING;
    }
}
