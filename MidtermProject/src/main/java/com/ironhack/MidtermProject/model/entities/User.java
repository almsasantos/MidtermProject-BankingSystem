package com.ironhack.MidtermProject.model.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer userId;
    protected String name;
    protected boolean isLogged;

    public User() {}

    public User(String name) {
        this.name = name;
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
