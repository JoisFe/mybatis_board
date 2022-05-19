package com.nhnacademy.jdbc.board.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
