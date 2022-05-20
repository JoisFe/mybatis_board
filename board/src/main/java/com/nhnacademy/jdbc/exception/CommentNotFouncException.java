package com.nhnacademy.jdbc.exception;

public class CommentNotFouncException extends IllegalArgumentException {
    public CommentNotFouncException(String message) {
        super(message);
    }
}
