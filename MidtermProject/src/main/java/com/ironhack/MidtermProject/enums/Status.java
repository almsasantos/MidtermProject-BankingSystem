package com.ironhack.MidtermProject.enums;

public enum Status {
    FROZEN("The Account is frozen and cannot be touched"),
    ACTIVE("The Account is active");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Status{" +
                "description='" + description + '\'' +
                '}';
    }

}
