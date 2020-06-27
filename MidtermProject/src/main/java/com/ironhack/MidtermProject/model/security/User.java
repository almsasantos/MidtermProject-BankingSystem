package com.ironhack.MidtermProject.model.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity for security
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * User's id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    /**
     * User's username
     */
    private String username;
    /**
     * User's password
     */
    private String password;

    /**
     * User's list of roles
     */
    @OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, mappedBy="user")
    private Set<Role> roles = new HashSet<>();

    /**
     * Void Constructor
     */
    public User() {}

    /**
     * Constructor of User
     * @param username Receives username for User
     * @param password Receives password for User
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of User's id
     * @return User's id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter of User's id
     * @param id Receives User's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter of User's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of User's username
     * @param username Receives User's username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @param password Receives User's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of User's Roles
     * @return List of  User's Roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setter of User's Roles
     * @param roles Receives list of User's Roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Representation of User in form of String
     * @return User in form of String
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + roles +
                '}';
    }
}