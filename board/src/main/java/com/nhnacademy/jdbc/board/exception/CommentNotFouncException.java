package com.nhnacademy.jdbc.board.exception;

public class CommentNotFouncException extends IllegalArgumentException {
    public CommentNotFouncException(String message) {
        super(message);
    }
}
