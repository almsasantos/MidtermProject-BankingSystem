package com.ironhack.MidtermProject.model.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User {
    public Admin() {}

    public Admin(String name) {
        super(name);
    }
}
