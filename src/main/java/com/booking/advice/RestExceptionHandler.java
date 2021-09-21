package com.booking.advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityAlreadyExistException.class})
    protected ResponseEntity<Object> handleUserAlreadyExist(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError("Entity already exist exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError("Entity not found exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }



}