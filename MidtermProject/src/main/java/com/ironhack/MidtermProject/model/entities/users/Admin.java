package com.ironhack.MidtermProject.model.entities.users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Admin Entity inherit from Users
 */
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends Users {
    /**
     * Void Constructor
     */
    public Admin() {}

    /**
     * Constructor
     * @param name Receives the admin name to constructor the Admin
     * @param password Receives the password to constructor the Admin
     */
    public Admin(String name, String password) {
        super(name, password);
    }
}
