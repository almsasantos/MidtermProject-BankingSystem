package com.ironhack.MidtermProject.model.entities.users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Users entity represents a user
 */
@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Users {
    /**
     * User's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer userId;
    /**
     * User's name must have a first name and a last name
     */
    @Pattern(regexp = "^(?:\\b\\w+\\b[\\s\\r\\n]*){2,7}$", message = "Name must be composed by firstname and lastname.")
    protected String name;
    /**
     * User's password that allows user to login
     */
    @NotNull(message = "Password cannot be null")
    protected String password;
    /**
     * isLogged tells if user is logged into their accounts
     */
    protected boolean isLogged;

    /**
     * Void constructor
     */
    public Users() {
        this.isLogged = false;
    }

    /**
     * Constructor
     * @param name Receives the user name to constructor the Users
     * @param password Receives the password to constructor the Users
     */
    public Users(String name, String password) {
        this.name = name;
        this.password = password;
        this.isLogged = false;
    }

    /**
     * Getter of User's id
     * @return User's id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Setter of User's id
     * @param userId Receives the User's id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Getter of User's name
     * @return User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of User's name
     * @param name Receives the User's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of User's password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of User's password
     * @param password Receives the User's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter of User's logged
     * @param logged Receives a boolean logged
     */
    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    /**
     * Void method that changes User's logged into true, meaning user is logged in
     */
    public void login(){
        setLogged(true);
    }

    /**
     * Void method that changes User's logged into false, meaning user is logged out
     */
    public void logOut(){
        setLogged(false);
    }

    /**
     * Getter User's isLogged
     * @return User's is Logged, meaning status of login
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Representation of User to String
     * @return User's information in String
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isLogged=" + isLogged +
                '}';
    }

    /**
     * Representation of User equals
     * @param o Receives an object
     * @return Boolean value depending if two users are equal or not
     */
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

    /**
     * Representation of User's hash code
     * @return User's hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, name, password, isLogged);
    }
}
