package com.ironhack.MidtermProject.model.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected boolean isLogged;

    public User() {
        this.isLogged = false;
    }

    public User(String name) {
        this.name = name;
        this.isLogged = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
