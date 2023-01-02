package org.example;

public enum ExceptionType {

    IO_EXCEPTION("Invalid file path");

    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}