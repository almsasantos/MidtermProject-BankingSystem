package com.ironhack.MidtermProject.model.entities.users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer userId;
    protected String name;
    protected String password;
    protected boolean isLogged;

    public User() {
        this.isLogged = false;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.isLogged = false;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(){
        this.isLogged = true;
    }

    public void logOut(){
        this.isLogged = false;
    }

    public boolean isLogged() {
        return isLogged;
    }
}
