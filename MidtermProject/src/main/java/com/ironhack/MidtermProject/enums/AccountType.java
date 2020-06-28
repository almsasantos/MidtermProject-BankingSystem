package com.ironhack.MidtermProject.enums;

/**
 * Enum to define the Valid Types of AccountType.
 */
public enum AccountType {
    SAVINGS("The type of Account is Saving"),
    CREDIT_CARD("The type of Account is CreditCard"),
    STUDENT_CHECKING("The type of Account is Student Checking"),
    CHECKING("The type of Account is Checking");

    /**
     * Description of each AccountType.
     */
    private String description;

    /**
     * AccountType Constructor.
     * @param description Receives a String with Account Type Description.
     */
    AccountType(String description) {
        this.description = description;
    }

    /**
     * Getter of AccountType's description.
     * @return a String with AccountType's description.
     */
    public String getDescription() {
        return description;
    }
}
