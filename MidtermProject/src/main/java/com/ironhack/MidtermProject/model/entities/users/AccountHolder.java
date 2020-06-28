package com.ironhack.MidtermProject.model.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.MidtermProject.model.entities.Address;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * AccountHolder entity inherit from Users
 */
@Entity
@Table(name = "account_holders")
@PrimaryKeyJoinColumn(name = "userId")
public class AccountHolder extends Users {
    /**
     * AccountHolder's date of birth
     */
    private LocalDate dateOfBirth;

    /**
     * AccountHolder's primary address
     */
    @Embedded
    private Address primaryAddress;

    /**
     * AccountHolder's mailing address
     */
    private String mailingAddress;

    /**
     * AccountHolder's list of account in which they are the primary owner
     */
    @JsonIgnore
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Account> accounts;

    /**
     * AccountHolder's list of account in which they are the secondary owner
     */
    @JsonIgnore
    @OneToMany(mappedBy = "secondaryOwner")
    private List<Account> secondaryAccounts;

    /**
     * Void Constructor
     */
    public AccountHolder() {}

    /**
     * Constructor
     * @param name Receives the accountHolder name to constructor the AccountHolder
     * @param password Receives the accountHolder password to constructor the AccountHolder
     * @param dateOfBirth Receives the accountHolder dateOfBirth to constructor the AccountHolder
     * @param primaryAddress Receives the accountHolder primaryAddress to constructor the AccountHolder
     */
    public AccountHolder(String name, String password, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = null;
        this.accounts = new ArrayList<>();
        this.secondaryAccounts = new ArrayList<>();
    }

    /**
     * Constructor
     * @param name Receives the accountHolder name to constructor the AccountHolder
     * @param password Receives the accountHolder password to constructor the AccountHolder
     * @param dateOfBirth Receives the accountHolder dateOfBirth to constructor the AccountHolder
     * @param primaryAddress Receives the accountHolder primaryAddress to constructor the AccountHolder
     * @param mailingAddress Receives the accountHolder mailingAddress to constructor the AccountHolder
     */
    public AccountHolder(String name, String password, LocalDate dateOfBirth, Address primaryAddress, String mailingAddress) {
        super(name, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accounts = new ArrayList<>();
        this.secondaryAccounts = new ArrayList<>();
    }

    /**
     * Getter of AccountHolder's date of birth
     * @return AccountHolder's date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Setter of AccountHolder's date of birth
     * @param dateOfBirth Receives AccountHolder's date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Getter of AccountHolder's primaryAddress
     * @return AccountHolder's primaryAddress
     */
    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Setter of AccountHolder's primaryAddress
     * @param primaryAddress Receives AccountHolder's primaryAddress
     */
    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    /**
     * Getter of AccountHolder's mailingAddress
     * @return AccountHolder's mailingAddress
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * Setter of AccountHolder's mailingAddress
     * @param mailingAddress Receives AccountHolder's mailingAddress
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * Getter of AccountHolder's accounts where account holder is the primary owner
     * @return AccountHolder's list of accounts where account holder is the primary owner
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Setter of AccountHolder's accounts where account holder is the primary owner
     * @param accounts Receives AccountHolder's list of accounts where account holder is the primary owner
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Getter of AccountHolder's accounts where account holder is the secondary owner
     * @return AccountHolder's list of accounts where account holder is the secondary owner
     */
    public List<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    /**
     * Setter of AccountHolder's accounts where account holder is the secondary owner
     * @param secondaryAccounts Receives AccountHolder's list of accounts where account holder is the secondary owner
     */
    public void setSecondaryAccounts(List<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }

    /**
     * Representation of AccountHolder in a String
     * @return AccountHolder in a form of String
     */
    @Override
    public String toString() {
        return "AccountHolder{" +
                "dateOfBirth=" + dateOfBirth +
                ", primaryAddress=" + primaryAddress +
                ", mailingAddress='" + mailingAddress + '\'' +
                '}';
    }
}