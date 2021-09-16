package com.booking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "An employee with this login already exist!")
public class EmployeeExistHandler extends RuntimeException {
}
