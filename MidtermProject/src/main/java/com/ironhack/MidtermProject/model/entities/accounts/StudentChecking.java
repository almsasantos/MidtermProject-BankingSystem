package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
    public StudentChecking() {
        this.accountType = AccountType.STUDENT_CHECKING;
    }

    public StudentChecking(Money balance, String secretKey, Status status) {
        super(balance, secretKey, status);
        this.accountType = AccountType.STUDENT_CHECKING;
    }

}
