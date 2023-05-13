package com.edu.ruse.studypal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotValidJsonBodyException extends RuntimeException{
    public NotValidJsonBodyException() {
    }

    public NotValidJsonBodyException(String message) {
        super(message);
    }
}
