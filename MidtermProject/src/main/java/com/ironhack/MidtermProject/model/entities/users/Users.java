package com.ironhack.MidtermProject.model.entities.users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer userId;
    @Pattern(regexp = "^(?:\\b\\w+\\b[\\s\\r\\n]*){2,7}$", message = "Name must be composed by firstname and lastname.")
    protected String name;
    @NotNull(message = "Password cannot be null")
    protected String password;
    protected boolean isLogged;

    public Users() {
        this.isLogged = false;
    }

    public Users(String name, String password) {
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isLogged=" + isLogged +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users user = (Users) o;
        return isLogged == user.isLogged &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, password, isLogged);
    }
}
