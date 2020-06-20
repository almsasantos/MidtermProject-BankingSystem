package com.ironhack.MidtermProject.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "third_party")
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User {
    //hashed key and a name
    private HashMap<String, String> accountDetails;

    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER)
    private List<Account> accounts;

    public ThirdParty(HashMap<String, String> accountDetails) {
        this.accountDetails = accountDetails;
        this.accounts = new ArrayList<Account>();
    }

    public ThirdParty(String name, HashMap<String, String> accountDetails) {
        super(name);
        this.accountDetails = accountDetails;
        this.accounts = new ArrayList<Account>();
    }

    public HashMap<String, String> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(HashMap<String, String> accountDetails) {
        this.accountDetails = accountDetails;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
