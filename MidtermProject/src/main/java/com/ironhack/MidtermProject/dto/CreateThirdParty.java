package com.ironhack.MidtermProject.dto;

import javax.validation.constraints.NotNull;

/**
 * CreateThirdParty class allows Admin to create a new ThirdParty entity.
 */
public class CreateThirdParty {
    /**
     * CreateThirdParty name, will define the name of ThirdParty user.
     */
    @NotNull(message = "Please insert a third party name")
    private String name;

    /**
     * CreateThirdParty hashedName, will define the name of ThirdParty company.
     */
    @NotNull(message = "Please insert the name of third's party company")
    private String hashedName;

    /**
     * CreateThirdParty password, will define the password of ThirdParty that will allow to login.
     */
    @NotNull(message = "Please insert the password that allows third party to login")
    private String password;

    /**
     * Getter of CreateThirdParty's name.
     * @return a string with the name of CreateThirdParty.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of CreateThirdParty's name.
     * @param name receives a string name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of CreateThirdParty's hashedName.
     * @return a string with hashedName of CreateThirdParty.
     */
    public String getHashedName() {
        return hashedName;
    }

    /**
     * Setter of CreateThirdParty's hashedName.
     * @param hashedName receives a string with hashedName.
     */
    public void setHashedName(String hashedName) {
        this.hashedName = hashedName;
    }

    /**
     * Getter of CreateThirdParty's password.
     * @return a string with password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of CreateThirdParty's password.
     * @param password receives a string with password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
