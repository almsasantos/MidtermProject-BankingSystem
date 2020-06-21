package com.ironhack.MidtermProject.model.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account_holders")
@PrimaryKeyJoinColumn(name = "userId")
public class AccountHolder extends User {
    private LocalDate dateOfBirth;
    @Embedded
    private Address primaryAddress;
    private String mailingAddress;

    //@JsonIgnore
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Account> accounts;

    public AccountHolder() {}

    public AccountHolder(String name, LocalDate dateOfBirth, Address primaryAddress) {
        super(name);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = null;
        this.accounts = new ArrayList<>();
    }

    public AccountHolder(String name, LocalDate dateOfBirth, Address primaryAddress, String mailingAddress) {
        super(name);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accounts = new ArrayList<>();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}