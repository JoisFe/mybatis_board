package com.nhnacademy.jdbc.board.exception;

public class NotAuthorizeException extends RuntimeException {
    public NotAuthorizeException(String message) {
        super(message);
    }
}
