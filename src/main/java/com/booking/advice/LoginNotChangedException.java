package com.booking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class LoginNotChangedException extends RuntimeException {

    public LoginNotChangedException(String message) {
        super(message);
    }
}
