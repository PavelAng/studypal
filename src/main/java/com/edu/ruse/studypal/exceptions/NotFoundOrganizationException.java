package com.edu.ruse.studypal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author anniexp
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundOrganizationException extends RuntimeException {
    public NotFoundOrganizationException() {
    }

    public NotFoundOrganizationException(String message) {
        super(message);
    }
}
