package com.nhnacademy.jdbc.board.exception;

public class LoveNotFoundException extends IllegalArgumentException {
    public LoveNotFoundException(String message) {
        super(message);
    }
}
