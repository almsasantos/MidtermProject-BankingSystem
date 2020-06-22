package com.ironhack.MidtermProject.model.entities.users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashMap;

@Entity
@Table(name = "third_party")
@PrimaryKeyJoinColumn(name = "userId")
public class ThirdParty extends User {
    //hashed key and a name
    private HashMap<String, String> accountDetails;

    public ThirdParty() {
        this.accountDetails = new HashMap<String, String>();
    }

    public ThirdParty(String name, String password) {
        super(name, password);
        this.accountDetails = new HashMap<String, String>();
    }

    public HashMap<String, String> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(HashMap<String, String> accountDetails) {
        this.accountDetails = accountDetails;
    }
}
