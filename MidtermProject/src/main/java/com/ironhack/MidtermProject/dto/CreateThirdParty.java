package com.ironhack.MidtermProject.dto;

public class CreateThirdParty {
    private String name;
    private String hashedName;
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
