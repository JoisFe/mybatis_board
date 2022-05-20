package com.nhnacademy.jdbc.exception;

public class MemberNotFoundException extends IllegalArgumentException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
