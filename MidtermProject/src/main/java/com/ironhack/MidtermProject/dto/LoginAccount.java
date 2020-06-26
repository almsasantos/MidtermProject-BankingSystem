package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;

public class LoginAccount {
    @NotNull(message = "Please insert your id")
    private Integer id;
    @NotNull(message = "Please insert your password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
