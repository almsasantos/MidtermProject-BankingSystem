package com.ironhack.MidtermProject.model.entities.users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Objects;

/**
 * Third Party Entity extends from Users
 */
@Entity
@Table(name = "third_party")
@PrimaryKeyJoinColumn(name = "userId")
public class ThirdParty extends Users {
    /**
     * Third Party accountDetails which is a HashMap of a random key and a name value
     */
    private HashMap<String, String> accountDetails;

    /**
     * Void constructor
     */
    public ThirdParty() {
        this.accountDetails = new HashMap<String, String>();
    }

    /**
     * Constructor
     * @param name Receives the third party name to constructor the Third Party
     * @param password Receives the password to constructor the Third Party
     */
    public ThirdParty(String name, String password) {
        super(name, password);
        this.accountDetails = new HashMap<String, String>();
    }

    /**
     * Getter of Third Party's accountDetails
     * @return Third Party's accountDetails
     */
    public HashMap<String, String> getAccountDetails() {
        return accountDetails;
    }

    /**
     * Setter of Third Party's accountDetails
     * @param accountDetails Receives the Third Party's accountDetails
     */
    public void setAccountDetails(HashMap<String, String> accountDetails) {
        this.accountDetails = accountDetails;
    }

    /**
     * Representation of Third Party in a String
     * @return Third Party in a String
     */
    @Override
    public String toString() {
        return "ThirdParty{" +
                "accountDetails=" + accountDetails +
                '}';
    }

    /**
     * Representation of Third Party equals
     * @param o Receives an object
     * @return Boolean value depending if two third parties are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThirdParty that = (ThirdParty) o;
        return Objects.equals(accountDetails, that.accountDetails);
    }

    /**
     * Representation of Third Party's hash code
     * @return Third Party's hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountDetails);
    }
}
