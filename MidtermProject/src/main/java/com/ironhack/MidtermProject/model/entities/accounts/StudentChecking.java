package com.ironhack.MidtermProject.model.entities.accounts;

import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.model.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
    @Pattern(regexp="[\\d]{6}", message = "Enter a valid secret key")
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> transactionsMade;

    public StudentChecking() {
        this.transactionsMade = new ArrayList<LocalDateTime>();
        this.accountType = AccountType.STUDENT_CHECKING;
    }

    public StudentChecking(Money balance, String secretKey, Status status) {
        super(balance);
        this.secretKey = secretKey;
        this.status = status;
        this.accountType = AccountType.STUDENT_CHECKING;
        this.transactionsMade = new ArrayList<LocalDateTime>();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<LocalDateTime> getTransactionsMade() {
        return transactionsMade;
    }

    public void setTransactionsMade(List<LocalDateTime> transactionsMade) {
        this.transactionsMade = transactionsMade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentChecking that = (StudentChecking) o;
        return Objects.equals(secretKey, that.secretKey) &&
                status == that.status &&
                Objects.equals(transactionsMade, that.transactionsMade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), secretKey, status, transactionsMade);
    }
}
