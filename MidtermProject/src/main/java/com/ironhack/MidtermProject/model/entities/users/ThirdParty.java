package com.ironhack.MidtermProject.model.entities.users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Objects;

@Entity
@Table(name = "third_party")
@PrimaryKeyJoinColumn(name = "userId")
public class ThirdParty extends Users {
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

    @Override
    public String toString() {
        return "ThirdParty{" +
                "accountDetails=" + accountDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThirdParty that = (ThirdParty) o;
        return Objects.equals(accountDetails, that.accountDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountDetails);
    }
}
