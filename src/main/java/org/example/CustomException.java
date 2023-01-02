package org.example;

public class CustomException extends Exception {
    public CustomException(ExceptionType type) {
        super(type.getMessage());
    }
}