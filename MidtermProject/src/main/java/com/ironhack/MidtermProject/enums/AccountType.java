package com.ironhack.MidtermProject.enums;

public enum AccountType {
    SAVING("The type of Account is Saving"),
    CREDIT_CARD("The type of Account is CreditCard"),
    STUDENT_CHECKING("The type of Account is Student Checking"),
    CHECKING("The type of Account is Checking");

    private String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
