package com.nhnacademy.jdbc.board.exception;

public class MemberNotFoundException extends IllegalArgumentException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
