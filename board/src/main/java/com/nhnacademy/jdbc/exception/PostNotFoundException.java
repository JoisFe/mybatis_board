package com.nhnacademy.jdbc.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
