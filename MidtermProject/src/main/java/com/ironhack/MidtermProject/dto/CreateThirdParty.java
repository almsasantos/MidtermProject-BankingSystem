package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;

public class CreateThirdParty {
    @NotNull(message = "Please insert a third party name")
    private String name;
    @NotNull(message = "Please insert the name of third's party company")
    private String hashedName;
    @NotNull(message = "Please insert the password that allows third party to login")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedName() {
        return hashedName;
    }

    public void setHashedName(String hashedName) {
        this.hashedName = hashedName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
