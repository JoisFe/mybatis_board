package com.nhnacademy.jdbc.board.exception;

public class NotMatchMemberIdException extends RuntimeException {
    public NotMatchMemberIdException(String message) {
        super(message);
    }
}
