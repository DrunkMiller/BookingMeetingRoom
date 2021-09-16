package com.booking.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeExistAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeExistHandler.class)
    protected ResponseEntity<EmployeeExistException> handleThisEmployeeExist() {
        return new ResponseEntity<>(new EmployeeExistException("An employee with this login already exist!"), HttpStatus.CONFLICT);
    }

    @Data
    @AllArgsConstructor
    public static class EmployeeExistException {
        public String message;
    }

}
