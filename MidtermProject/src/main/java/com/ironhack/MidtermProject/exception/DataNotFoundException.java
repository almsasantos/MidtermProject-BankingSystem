package com.ironhack.MidtermProject.exception;

public class DataNotFoundException extends RuntimeException {
    /**
     * Throws an Exception if a matching criteria is not met.
     * @param message Throws a custom message to the user.
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
