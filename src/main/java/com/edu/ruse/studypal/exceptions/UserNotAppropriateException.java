package com.edu.ruse.studypal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotAppropriateException extends RuntimeException {
    public UserNotAppropriateException() {
    }

    public UserNotAppropriateException(String message) {
        super(message);
    }
}
