package com.hanghae.justpotluck.global.exception;

public class EmailAuthTokenNotFountException extends RuntimeException{
    public EmailAuthTokenNotFountException() {
    }

    public EmailAuthTokenNotFountException(String message) {
        super(message);
    }

    public EmailAuthTokenNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
