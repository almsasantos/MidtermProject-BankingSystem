package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;

/**
 * LoginAccount class will allow any type of User to Login into their account
 */
public class LoginAccount {
    /**
     * LoginAccount id which corresponds to the id of the User.
     */
    @NotNull(message = "Please insert your id")
    private Integer id;

    /**
     * LoginAccount password which corresponds to the password of that User's id.
     */
    @NotNull(message = "Please insert your password")
    private String password;

    /**
     * Getter of LoginAccount's id.
     * @return an integer with id of User.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of LoginAccount's id.
     * @param id receives an integer of the User's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of LoginAccount's password.
     * @return a string with User's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of LoginAccount's password.
     * @param password a string of password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
