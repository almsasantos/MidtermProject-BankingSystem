package com.ironhack.MidtermProject.model.entities.users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends Users {
    public Admin() {}

    public Admin(String name, String password) {
        super(name, password);
    }
}
