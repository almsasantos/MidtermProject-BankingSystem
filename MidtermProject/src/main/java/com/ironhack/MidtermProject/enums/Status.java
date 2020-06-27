package com.ironhack.MidtermProject.enums;

/**
 * Enum to define the Valid Status of Accounts
 */
public enum Status {
    FROZEN("The Account is frozen and cannot be touched"),
    ACTIVE("The Account is active");

    /**
     * Description of each Status
     */
    private String description;

    /**
     * Status Constructor.
     * @param description Receives a String with Account Type Description.
     */
    Status(String description) {
        this.description = description;
    }

    /**
     * Getter of Status' description.
     * @return a String with Status' description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Representation of status in form of String.
     * @return a String with status.
     */
    @Override
    public String toString() {
        return "Status{" +
                "description='" + description + '\'' +
                '}';
    }

}
