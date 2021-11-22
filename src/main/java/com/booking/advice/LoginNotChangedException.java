package com.booking.advice;

public class LoginNotChangedException extends RuntimeException {

    public LoginNotChangedException(String message) {
        super(message);
    }
}
