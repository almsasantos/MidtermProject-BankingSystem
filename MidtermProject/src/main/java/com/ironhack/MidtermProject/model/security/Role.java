package com.ironhack.MidtermProject.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Role entity for secutiry
 */
@Entity
@Table(name="role")
public class Role {
    /**
     * Role id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Type of Role
     */
    private String role;

    /**
     * User associated with that role
     */
    @ManyToOne
    @JsonIgnore
    private User user;

    /**
     * Void Constructor
     */
    public Role() {}

    /**
     * Constructor of Role
     * @param role Receives the type of role
     */
    public Role(String role) {
        this.role = role;
    }

    /**
     * Getter of Role's id
     * @return Role's id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter of Role's id
     * @param id Receives Role's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter of Role's type of role
     * @return Role's type of role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter of Role's type of role
     * @param role Receives Role's type of role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Getter of Role's user
     * @return Role's user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of Role's user
     * @param user Receives Role's user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Representation of role in form of a String
     * @return Role in form of a String
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + role + '\'';
    }
}

